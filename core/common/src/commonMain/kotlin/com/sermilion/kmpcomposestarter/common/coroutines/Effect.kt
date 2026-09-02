package com.sermilion.kmpcomposestarter.common.coroutines

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * The one one-off-effect idiom in this app: navigate, show a snackbar, dismiss a sheet.
 *
 * A `MutableSharedFlow()` with the default zero replay drops anything emitted while no collector
 * is attached, and a screen has no collector between leaving the composition and re-entering it —
 * so a navigation request made in that window was silently lost. A [Channel] buffers instead, and
 * hands each value to exactly one collector.
 *
 * Use this and only this for one-off effects; state belongs in a `StateFlow`, not here.
 */
class Effect<T> {
  private val channel = Channel<T>(Channel.BUFFERED)

  /**
   * Single-consumer by construction: a value delivered to one collector is gone.
   *
   * Whatever is still queued when a collector goes away goes with it. A screen that queued two
   * navigations and left on the first would otherwise replay the second the next time it entered
   * composition, bouncing the user straight back out. Anything emitted after the collector is
   * gone still waits in the buffer for the next one, which is the point of the channel.
   */
  val flow: Flow<T> = channel.receiveAsFlow().onCompletion { dropQueuedEffects() }

  suspend fun emit(value: T) {
    channel.send(value)
  }

  private fun dropQueuedEffects() {
    var queued = channel.tryReceive()
    while (queued.isSuccess) {
      queued = channel.tryReceive()
    }
  }
}
