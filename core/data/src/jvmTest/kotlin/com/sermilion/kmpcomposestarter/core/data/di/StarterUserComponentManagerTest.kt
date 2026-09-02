package com.sermilion.kmpcomposestarter.core.data.di

import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking
import java.util.concurrent.CyclicBarrier

private fun user(index: Int) = UserData("id-$index", "user$index@test.com", "User $index")

class StarterUserComponentManagerTest :
  FunSpec({

    test("a second login for a different user replaces and tears down the first session") {
      val factory = RecordingUserComponentFactory()
      val manager = StarterUserComponentManager(factory)

      val first = manager.createComponent(user(1)) as FakeUserComponent
      val second = manager.createComponent(user(2)) as FakeUserComponent

      manager.userComponent shouldBe second
      manager.userComponent?.userData shouldBe user(2)
      factory.created shouldHaveSize 2

      first.closeCount shouldBe 1
      first.userSessionScope.isActive shouldBe false
      second.closeCount shouldBe 0
      second.userSessionScope.isActive shouldBe true
    }

    test("creating a session for the already signed-in user is a no-op") {
      val factory = RecordingUserComponentFactory()
      val manager = StarterUserComponentManager(factory)

      val first = manager.createComponent(user(1))
      val again = manager.createComponent(user(1))

      again shouldBe first
      factory.created shouldHaveSize 1
      (first as FakeUserComponent).closeCount shouldBe 0
    }

    test("concurrent creates leave exactly one live session and close every loser once") {
      val factory = RecordingUserComponentFactory()
      val manager = StarterUserComponentManager(factory)
      val userCount = 8
      val barrier = CyclicBarrier(userCount)

      runBlocking {
        (0 until userCount)
          .map { index ->
            async(Dispatchers.Default) {
              barrier.await()
              manager.createComponent(user(index))
            }
          }.awaitAll()
      }

      val live = manager.userComponent
      live shouldBe factory.created.single { it.closeCount == 0 }
      factory.created.filter { it !== live }.forEach { loser ->
        loser.closeCount shouldBe 1
        loser.userSessionScope.isActive shouldBe false
      }
    }

    test("destroyComponent cancels the session scope and closes every user-scoped resource") {
      val factory = RecordingUserComponentFactory()
      val manager = StarterUserComponentManager(factory)
      val component = manager.createComponent(user(1)) as FakeUserComponent

      manager.destroyComponent()

      manager.userComponent shouldBe null
      manager.userComponentFlow.value shouldBe null
      component.closeCount shouldBe 1
      component.userSessionScope.isActive shouldBe false

      manager.destroyComponent()
      component.closeCount shouldBe 1
    }

    test("sign-ins racing sign-outs never publish a session that has been torn down") {
      val factory = RecordingUserComponentFactory()
      val manager = StarterUserComponentManager(factory)
      val callerCount = 8
      val barrier = CyclicBarrier(callerCount)

      runBlocking {
        (0 until callerCount)
          .map { index ->
            async(Dispatchers.Default) {
              barrier.await()
              if (index % 2 == 0) {
                manager.createComponent(user(index))
              } else {
                manager.destroyComponent()
              }
            }
          }.awaitAll()
      }

      val live = manager.userComponent
      (live as FakeUserComponent?)?.let {
        it.closeCount shouldBe 0
        it.userSessionScope.isActive shouldBe true
      }
      factory.created.filter { it !== live }.forEach { closed ->
        closed.closeCount shouldBe 1
        closed.userSessionScope.isActive shouldBe false
      }
    }
  })
