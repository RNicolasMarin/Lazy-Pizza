package com.lazy.pizza.core.presentation.designsystem

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val home: DimensHome,
    val detail: DimensDetail,
)

data class DimensHome(
    val paddingHorizontal: Dp
)

data class DimensDetail(
    val paddingHorizontal: Dp,
    val addCartOverlay: Dp
)

val dimensPhonePortrait = Dimens(
    home = DimensHome(
        paddingHorizontal = 16.dp
    ),
    detail = DimensDetail(
        paddingHorizontal = 16.dp,
        addCartOverlay = 100.dp
    )
)

val dimensTabletPortrait = Dimens(
    home = DimensHome(
        paddingHorizontal = 16.dp
    ),
    detail = DimensDetail(
        paddingHorizontal = 16.dp,
        addCartOverlay = 100.dp
    )
)