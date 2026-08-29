package com.sermilion.kmpcomposestarter.core.data.session

import com.sermilion.kmpcomposestarter.core.data.auth.DataStoreTokenStore
import com.sermilion.kmpcomposestarter.core.data.db.DatabaseProvider
import com.sermilion.kmpcomposestarter.core.data.db.dao.StarterUserDao
import com.sermilion.kmpcomposestarter.core.data.db.dao.UserDao
import com.sermilion.kmpcomposestarter.core.data.db.room.PlatformRoomDatabaseBuilderFactory
import com.sermilion.kmpcomposestarter.core.data.db.room.StarterRoomDatabaseProvider
import com.sermilion.kmpcomposestarter.core.data.di.StarterUserComponentManager
import com.sermilion.kmpcomposestarter.core.data.di.UserComponent
import com.sermilion.kmpcomposestarter.core.data.local.AuthLocalDataSource
import com.sermilion.kmpcomposestarter.core.data.local.DataStoreAuthLocalDataSource
import com.sermilion.kmpcomposestarter.core.data.model.AuthResultDataModel
import com.sermilion.kmpcomposestarter.core.data.model.AuthTokenDataModel
import com.sermilion.kmpcomposestarter.core.data.model.UserDataModel
import com.sermilion.kmpcomposestarter.core.data.remote.AuthRemoteDataSource
import com.sermilion.kmpcomposestarter.core.data.repository.StarterAuthRepository
import com.sermilion.kmpcomposestarter.core.data.repository.StarterUserRepository
import com.sermilion.kmpcomposestarter.core.data.testing.RealDispatcherProvider
import com.sermilion.kmpcomposestarter.core.datastore.createUserPreferencesDataStore
import com.sermilion.kmpcomposestarter.core.domain.auth.TokenStore
import com.sermilion.kmpcomposestarter.core.domain.di.ScreenComponent
import com.sermilion.kmpcomposestarter.core.domain.di.UserScopedCloseable
import com.sermilion.kmpcomposestarter.core.domain.di.UserSessionScope
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import com.sermilion.kmpcomposestarter.core.domain.repository.UserRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.runTest
import okio.Path.Companion.toOkioPath
import java.nio.file.Files

private val userA = UserDataModel("integration-user-a", "a@test.com", "Ada")
private val userB = UserDataModel("integration-user-b", "b@test.com", "Brendan")

private class StubAuthRemoteDataSource : AuthRemoteDataSource {

  override suspend fun login(email: String, password: String): AuthResultDataModel {
    val user = if (email == userA.email) userA else userB
    return AuthResultDataModel.Success(user, AuthTokenDataModel("token-for-${user.id}"))
  }

  override suspend fun register(
    email: String,
    password: String,
    name: String,
  ): AuthResultDataModel = login(email, password)

  override suspend fun logout() = Unit
}

/**
 * The wiring `UserScopeModule` declares, assembled by hand because the generated subcomponent only
 * exists inside an application graph. Everything below it is the production implementation.
 */
private class TestUserComponent(
  override val userData: UserData,
  localDataSource: AuthLocalDataSource,
  databaseProvider: DatabaseProvider,
) : UserComponent {

  val userDao: UserDao = StarterUserDao(databaseProvider.provideUserDatabase(userData.id))

  override val tokenStore: TokenStore = DataStoreTokenStore(localDataSource, userData)

  override val userRepository: UserRepository = StarterUserRepository(
    userDao = userDao,
    userData = userData,
    databaseProvider = databaseProvider,
    dispatcherProvider = RealDispatcherProvider,
  )

  override val userSessionScope: UserSessionScope =
    UserSessionScope(CoroutineScope(SupervisorJob()))

  override val userScopedCloseables: Set<UserScopedCloseable> = setOf(
    UserScopedCloseable { databaseProvider.closeDatabaseForUser(userData.id) },
  )

  override val screenComponentFactory: ScreenComponent.Factory =
    object : ScreenComponent.Factory {
      override fun create(): ScreenComponent = error("screen components are out of scope here")
    }
}

class SecondLoginIntegrationTest :
  FunSpec({

    test("a second sign-in reuses nothing from the first user's session") {
      runTest {
        val preferencesDirectory = Files.createTempDirectory("second-login-prefs").toFile()
        val dataStoreScope = CoroutineScope(SupervisorJob() + RealDispatcherProvider.io)
        val localDataSource = DataStoreAuthLocalDataSource(
          createUserPreferencesDataStore(dataStoreScope) {
            preferencesDirectory.resolve("user_preferences.json").toOkioPath()
          },
        )
        val databaseProvider = StarterRoomDatabaseProvider(
          builderFactory = PlatformRoomDatabaseBuilderFactory(),
          dispatcherProvider = RealDispatcherProvider,
        )
        // Databases live in a directory shared with the rest of the suite, so start from a
        // known-empty state rather than inheriting a previous run's rows.
        databaseProvider.deleteDatabaseForUser(userA.id)
        databaseProvider.deleteDatabaseForUser(userB.id)

        val manager = StarterUserComponentManager(
          object : UserComponent.Factory {
            override fun create(userData: UserData): UserComponent =
              TestUserComponent(userData, localDataSource, databaseProvider)
          },
        )
        val repository = StarterAuthRepository(
          remoteDataSource = StubAuthRemoteDataSource(),
          userComponentManager = manager,
          externalScope = backgroundScope,
        )

        try {
          repository.login(userA.email, "password")
          val sessionA = manager.userComponent as TestUserComponent

          sessionA.tokenStore.get()?.accessToken shouldBe "token-for-${userA.id}"
          sessionA.userDao.findUser(userA.id).shouldNotBeNull()
          localDataSource.getSession()?.user?.id shouldBe userA.id

          repository.logout()

          manager.userComponent shouldBe null
          localDataSource.getSession() shouldBe null

          repository.login(userB.email, "password")
          val sessionB = manager.userComponent as TestUserComponent

          sessionB.tokenStore.get()?.accessToken shouldBe "token-for-${userB.id}"
          localDataSource.getSession()?.user?.id shouldBe userB.id
          sessionB.userDao.findUser(userB.id).shouldNotBeNull()
          // The one that matters: a database keyed per user means the second user cannot read
          // the first user's rows, however the sessions were opened and closed.
          sessionB.userDao.findUser(userA.id) shouldBe null
        } finally {
          manager.destroyComponent()
          databaseProvider.deleteDatabaseForUser(userA.id)
          databaseProvider.deleteDatabaseForUser(userB.id)
          dataStoreScope.cancel()
          preferencesDirectory.deleteRecursively()
        }
      }
    }
  })
