# Persistence

## Current Persistence Stack

Local relational persistence uses Room 3.

The starter currently defines two databases in `core:data`:

- `UserDatabase`
- `OnboardingDatabase`

Room schemas are exported and committed under `core/data/schemas/`.

## Database Lifetime Model

The shared `DatabaseProvider` exposes:

- a per-user `UserDatabase`
- a shared onboarding database
- deletion and cache-clearing hooks

`StarterRoomDatabaseProvider` caches opened database instances by filename so repeated requests reuse the same connection-backed database object.

User database filenames are derived from the user ID, which keeps authenticated user data isolated per local user session.

## Platform Builders

Database creation is delegated through `PlatformRoomDatabaseBuilderFactory`.

That keeps file-path and driver behavior platform-specific while the higher-level provider stays shared.

### Android and iOS

Android and iOS builders use Room 3 with the bundled SQLite driver.

### JVM

The JVM path uses a small JDBC-backed `SQLiteDriver` wrapper.

That exists because the bundled native SQLite path was not reliable for the current local JVM test environment, especially on macOS x86_64 hosts.

Treat it as an implementation detail of the JVM builder, not as a signal to add JDBC-style persistence APIs elsewhere in the codebase.

## DAO Layer Guidance

The higher-level `StarterUserDao` remains a shared wrapper over the Room-generated DAO.

That wrapper is a good place to:

- map between persistence models and local/domain models
- keep UUID and time conversions centralized
- expose both single-item and bulk write operations

Prefer extending wrappers like this instead of spreading entity-to-domain mapping logic throughout calling code.

## DataStore Boundary

Use `core:datastore` for preferences and lightweight structured settings.

Use Room for relational entities, queryable collections, and user-scoped local data.

If a DataStore file ever needs to be renamed, treat that as a migration instead of a harmless refactor.
