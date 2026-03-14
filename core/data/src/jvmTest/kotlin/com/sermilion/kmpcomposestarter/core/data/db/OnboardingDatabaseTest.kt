package com.sermilion.kmpcomposestarter.core.data.db

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import java.nio.file.Files

class OnboardingDatabaseTest :
  FunSpec({
    test("onboarding tag dao can upsert and query selected tags") {
      runTest {
        val databaseDirectory = Files.createTempDirectory("room3-onboarding-db").toFile()
        val databasePath = databaseDirectory.resolve("onboarding-test.db").absolutePath
        val database = createOnboardingDatabase(
          createOnboardingDatabaseBuilder(
            databaseFileName = "onboarding-test.db",
            path = databasePath,
          ),
        )

        try {
          val selected =
            OnboardingTagDataModel(id = "selected", name = "Selected", isSelected = true)
          val unselected = OnboardingTagDataModel(id = "plain", name = "Plain", isSelected = false)

          database.onboardingTagEntityDao().upsertAll(listOf(selected, unselected))

          database.onboardingTagEntityDao().observeSelected().first() shouldContainExactly
            listOf(selected)
        } finally {
          database.close()
          databaseDirectory.deleteRecursively()
        }
      }
    }
  })
