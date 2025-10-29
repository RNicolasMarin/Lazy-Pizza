package com.lazy.pizza.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lazy.pizza.core.presentation.designsystem.InstrumentSansSemiBold
import com.lazy.pizza.core.presentation.designsystem.PrimaryGradientEnd
import com.lazy.pizza.core.presentation.designsystem.PrimaryGradientStart
import com.lazy.pizza.core.presentation.designsystem.TextOnPrimary

@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    horizontalPadding: Dp,
    verticalPadding: Dp,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 100.dp
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .shadow(
                elevation = 6.dp,
                spotColor = Color(0x40F36B50), // #F36B5040 shadow
                shape = RoundedCornerShape(cornerRadius) // pill shape
            )
            .clip(RoundedCornerShape(cornerRadius))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        PrimaryGradientEnd, // right (start for 270deg)
                        PrimaryGradientStart // left
                    )
                )
            )
            .clickable(
                onClick = onClick
            )
            .padding(
                horizontal = horizontalPadding,
                vertical = verticalPadding
            )
    ) {
        Text(
            text = text,
            style = InstrumentSansSemiBold.copy(
                fontSize = 15.sp,
                lineHeight = 22.sp
            ),
            color = TextOnPrimary
        )
    }
}