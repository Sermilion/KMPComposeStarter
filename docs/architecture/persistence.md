# Persistence

## Current Persistence Stack

Local relational persistence uses Room 3, and session/preference persistence uses AndroidX
DataStore through `core:datastore`.

The starter currently defines one database in `core:data`:

- `UserDatabase`

Room schemas are exported and committed under `core/data/schemas/`.

## Database Lifetime Model

The shared `DatabaseProvider` exposes:

- a per-user `UserDatabase`
- close and delete hooks keyed by user ID

`UserScopeModule` provides the session's `UserDatabase` from `provideUserDatabase(userData.id)`, so
a database is opened when a session opens and is bound to the user who owns it. The same module
multibinds a `UserScopedCloseable` that calls `closeDatabaseForUser`, so signing out releases the
file instead of handing a cached instance to whoever signs in next.

`StarterRoomDatabaseProvider` caches opened database instances by filename so repeated requests
reuse the same connection-backed database object.

Database filenames are `user_<sha256(userId)>.db`. The ID is hashed rather than interpolated:
user IDs are opaque server strings that may contain path separators, and a raw ID in a filename
would also print the user into every disk error.

### Deleting a user's data

`deleteDatabaseForUser` closes the database, then removes the main file **and its `-wal`/`-shm`
sidecars**. In WAL mode a database is three files, and committed rows can still be sitting in the
write-ahead log, so deleting only the main file would leave them recoverable. It returns whether
every file is gone; callers must not discard that result. `StarterUserRepository.deleteMyData()`
surfaces it, and the Profile screen refuses to report success on a `false`.

The call is **terminal for the session's data reads whichever way it returns**. The database has to
be closed before the files are touched — deleting a file an open connection still holds either
fails outright or leaves that connection writing to an orphaned inode — and the instance handed to
the session's `UserDatabase` and `StarterUserDao` cannot be un-closed. So a failed deletion leaves
the user signed in with data still on disk that this session can no longer read. That is the
deliberate trade: signing the user out would claim the data is gone, and retrying into a working
handle is not something the caller can do. Profile stops following the stored row, keeps the values
already on screen, and reports the failure; the next sign-in opens a fresh database.

### Migrations

Every schema change bumps `UserDatabase`'s version, ships a numbered `Migration` registered on
`createUserDatabase`, and re-exports the schema under `core/data/schemas/`. A commented
`MIGRATION_1_2` template sits next to the builder as the starting point. Never reach for
`fallbackToDestructiveMigration` — it deletes the user's data to make a build compile.

## Platform Builders

Database creation is delegated through `PlatformRoomDatabaseBuilderFactory`.

That keeps file-path and deletion behavior platform-specific while the higher-level provider stays
shared.

All three platforms use Room 3 with `BundledSQLiteDriver` and set
`setQueryCoroutineContext(dispatcherProvider.io)`, so every target runs the same SQLite build and
no query runs on the caller's thread.

On desktop, files live in `~/.kmpcomposestarter` unless `-Dstarter.dataDir=...` overrides it. The
Gradle test tasks in `core:data` set that property to a build-directory path, so running tests
never writes into the developer's home directory.

## DAO Layer Guidance

The higher-level `StarterUserDao` remains a shared wrapper over the Room-generated DAO.

That wrapper is a good place to:

- map between the Room entity (`UserEntity`) and local/domain models
- keep ID and time conversions centralized
- expose both single-item and bulk write operations

Prefer extending wrappers like this instead of spreading entity-to-domain mapping logic throughout
calling code.

Mappers stay pure: `UserData.toLocalDataModel(createdAt)` takes the timestamp as a parameter
rather than reading the clock, so a re-login does not silently reset a row's creation time.

## DataStore Boundary

Use `core:datastore` for preferences and lightweight structured settings.

It owns `UserPreferences`, an `@Serializable` document persisted as JSON through an
`OkioSerializer`, with a `ReplaceFileCorruptionHandler` that falls back to empty preferences rather
than crashing at launch. `UserPreferencesPathProvider` supplies the per-platform file location.

`core:data`'s `DataStoreAuthLocalDataSource` is its consumer: it stores the signed-in session
(identity plus token) in one write, and `SessionRestorer` reads it at launch to rebuild the session
before the shell picks a back stack.

> **Before shipping:** the bearer token lands in plain JSON on disk. That is fine for a template
> with a mock backend and not fine for a real one. Move the token to the iOS Keychain, Android
> `EncryptedFile`/Keystore, and an OS keyring on desktop, and keep only non-secret session metadata
> in `UserPreferences`.

Use Room for relational entities, queryable collections, and user-scoped local data.

If a DataStore file ever needs to be renamed, treat that as a migration instead of a harmless
refactor.
