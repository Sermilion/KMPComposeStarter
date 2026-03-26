package news.readian.notoesapp.core.data.db

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import java.nio.file.Files

class UserDatabaseTest :
  FunSpec({
    test("user dao can upsert and query entities") {
      runTest {
        val databaseDirectory = Files.createTempDirectory("room3-user-db").toFile()
        val databasePath = databaseDirectory.resolve("user-test.db").absolutePath
        val database = createUserDatabase(
          createUserDatabaseBuilder(
            databaseFileName = "user-test.db",
            path = databasePath,
          ),
        )

        try {
          val user =
            UserDataModel(
              id = "user-1",
              name = "Sample User",
              email = "sample@example.com",
              createdAt = 1L,
            )

          database.userEntityDao().upsert(user)

          database.userEntityDao().getById(user.id) shouldBe user
          database.userEntityDao().observeUsers().first() shouldContainExactly listOf(user)
        } finally {
          database.close()
          databaseDirectory.deleteRecursively()
        }
      }
    }
  })
