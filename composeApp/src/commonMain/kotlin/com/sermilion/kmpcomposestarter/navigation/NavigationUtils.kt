package com.sermilion.kmpcomposestarter.navigation

import androidx.compose.runtime.snapshots.SnapshotStateList

fun <T> snapshotStateListOf(vararg elements: T): SnapshotStateList<T> {
  val list = SnapshotStateList<T>()
  list.addAll(elements)
  return list
}

fun <T> List<T>.toSnapshotStateList(): SnapshotStateList<T> {
  val list = SnapshotStateList<T>()
  list.addAll(this)
  return list
}
