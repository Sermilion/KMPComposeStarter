package news.readian.notoesapp.core.data.db

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface DatabaseProvider {
  fun provideUserDatabase(userId: Uuid): UserDatabase
  fun provideOnboardingDatabase(): OnboardingDatabase
  fun deleteDatabaseForUser(userId: Uuid)
  fun clearCachedInstances()
}
