package com.lazy.pizza.core.presentation.designsystem

import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.lazy.pizza.core.presentation.designsystem.ScreenConfiguration.*

private val ColorScheme = lightColorScheme(
    primary = Primary,
)

@Composable
fun LazyPizzaTheme(
    content: @Composable () -> Unit
) {
    val configuration = LocalConfiguration.current
    val heightDp = configuration.screenHeightDp
    val widthDp = configuration.screenWidthDp

    Log.d("ScreenSize", "widthDp = $widthDp, heightDp = $heightDp")

    val screenConfiguration = when {
        widthDp < 840 -> PHONE_PORTRAIT
        else -> TABLET_PORTRAIT
    }

    val dimens = when (screenConfiguration) {
        PHONE_PORTRAIT -> dimensPhonePortrait
        TABLET_PORTRAIT -> dimensTabletPortrait
    }

    ProvideDimens(dimens) {
        ProvideScreenConfiguration(screenConfiguration) {
            MaterialTheme(
                colorScheme = ColorScheme,
                typography = Typography,
                content = content
            )
        }
    }
}