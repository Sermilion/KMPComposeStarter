package com.sermilion.kmpcomposestarter.core.data.db

import com.sermilion.kmpcomposestarter.core.data.db.room.PlatformRoomDatabaseBuilderFactory
import com.sermilion.kmpcomposestarter.core.data.db.room.StarterRoomDatabaseProvider
import com.sermilion.kmpcomposestarter.core.data.db.room.userDatabaseFileName
import com.sermilion.kmpcomposestarter.core.data.testing.RealDispatcherProvider
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import java.io.File
import java.nio.file.Files

class UserDatabaseTest :
  FunSpec({

    test("user dao can upsert and query entities") {
      runTest {
        val databaseDirectory = Files.createTempDirectory("room3-user-db").toFile()
        val database =
          createUserDatabase(
            createUserDatabaseBuilder(
              databaseFileName = "user-test.db",
              queryContext = Dispatchers.IO,
              path = databaseDirectory.resolve("user-test.db").absolutePath,
            ),
          )

        try {
          val user =
            UserEntity(
              id = "user-1",
              name = "Sample User",
              email = "sample@example.com",
              createdAt = 1L,
            )

          database.userEntityDao().upsert(user)

          database.userEntityDao().getById(user.id) shouldBe user
          database.userEntityDao().observeById(user.id).first() shouldBe user
        } finally {
          database.close()
          databaseDirectory.deleteRecursively()
        }
      }
    }

    test("delete-my-data removes the database and its wal and shm sidecars") {
      runTest {
        val provider =
          StarterRoomDatabaseProvider(
            builderFactory = PlatformRoomDatabaseBuilderFactory(),
            dispatcherProvider = RealDispatcherProvider,
          )
        val userId = "user-to-erase"
        val files =
          databaseFilePaths(defaultDatabasePath(userDatabaseFileName(userId)))
            .map(::File)

        provider.deleteDatabaseForUser(userId)

        provider.provideUserDatabase(userId).userEntityDao().upsert(
          UserEntity(id = userId, name = "Erasable", email = null, createdAt = 1L),
        )
        files.first().exists() shouldBe true

        provider.deleteDatabaseForUser(userId) shouldBe true

        files.filter { it.exists() } shouldBe emptyList()
      }
    }
  })
