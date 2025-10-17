package com.lazy.pizza.core.presentation.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
fun ProvideScreenConfiguration(
    screenConfiguration: ScreenConfiguration,
    content: @Composable () -> Unit
) {
    val screenConfigurationSet = remember { screenConfiguration }
    CompositionLocalProvider(
        LocalScreenConfiguration provides screenConfigurationSet,
        content = content
    )
}

private val LocalScreenConfiguration = staticCompositionLocalOf {
    ScreenConfiguration.PHONE_PORTRAIT
}

val MaterialTheme.screenConfiguration: ScreenConfiguration
    @Composable
    get() = LocalScreenConfiguration.current