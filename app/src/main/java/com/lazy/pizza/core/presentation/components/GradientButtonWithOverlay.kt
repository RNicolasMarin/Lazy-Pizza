package com.lazy.pizza.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lazy.pizza.core.presentation.designsystem.DimensGradientButton
import com.lazy.pizza.core.presentation.designsystem.dimen

@Composable
fun GradientButtonWithOverlay(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    padding: Dp = 0.dp,
    dimens: DimensGradientButton = MaterialTheme.dimen.gradientButton,
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = modifier
            .height(dimens.addCartOverlay)
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFFFFF),          // solid white at start
                        Color(0xFFFFFFFF).copy(alpha = 0f) // transparent at end
                    ),
                    startY = Float.POSITIVE_INFINITY, // 0deg → bottom to top
                    endY = 0f
                )
            )
            .padding(
                horizontal = padding,
                vertical = dimens.paddingHorizontal
            )
    ) {
        GradientButton(
            text = text,
            onClick = onClick,
            horizontalPadding = 12.dp,
            verticalPadding = 12.dp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}