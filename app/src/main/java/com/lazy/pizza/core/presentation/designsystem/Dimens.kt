package com.lazy.pizza.core.presentation.designsystem

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val home: DimensHome
)

data class DimensHome(
    val paddingHorizontal: Dp
)

val dimensPhonePortrait = Dimens(
    home = DimensHome(
        paddingHorizontal = 16.dp
    )
)

val dimensTabletPortrait = Dimens(
    home = DimensHome(
        paddingHorizontal = 16.dp
    )
)