package com.lazy.pizza.core.presentation.designsystem

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val home: DimensHome,
    val detail: DimensDetail,
    val nav: DimensNav,
    val gradientButton: DimensGradientButton
)

data class DimensGradientButton(
    val addCartOverlay: Dp,
    val paddingHorizontal: Dp,
)

data class DimensNav(
    val cardAndNumberWidth: Dp,
    val spaceBeforeNumberHeight: Dp,
    val horizontalIconPadding: Dp,
    val bottomIconPadding: Dp,
)

data class DimensHome(
    val paddingHorizontal: Dp,
    val columnsAmount: Int
)

data class DimensDetail(
    val paddingHorizontal: Dp,
)

val dimensPhonePortrait = Dimens(
    home = DimensHome(
        paddingHorizontal = 16.dp,
        columnsAmount = 1
    ),
    detail = DimensDetail(
        paddingHorizontal = 16.dp
    ),
    nav = DimensNav(
        cardAndNumberWidth = 40.dp,
        spaceBeforeNumberHeight = 6.dp,
        horizontalIconPadding = 28.dp,
        bottomIconPadding = 10.dp
    ),
    gradientButton = DimensGradientButton(
        addCartOverlay = 100.dp,
        paddingHorizontal = 16.dp
    )
)

val dimensTabletPortrait = Dimens(
    home = DimensHome(
        paddingHorizontal = 16.dp,
        columnsAmount = 2
    ),
    detail = DimensDetail(
        paddingHorizontal = 16.dp
    ),
    nav = DimensNav(
        cardAndNumberWidth = 54.dp,
        spaceBeforeNumberHeight = 4.dp,
        horizontalIconPadding = 0.dp,
        bottomIconPadding = 8.dp
    ),
    gradientButton = DimensGradientButton(
        addCartOverlay = 100.dp,
        paddingHorizontal = 16.dp
    )
)