package com.sermilion.kmpcomposestarter

actual fun createIosComponent(): IosApplicationComponent {
  return IosApplicationComponent::class.create()
}
