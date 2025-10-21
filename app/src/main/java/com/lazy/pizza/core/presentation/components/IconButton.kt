package com.lazy.pizza.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 8.dp,
    iconColor: Color,
    border: Color,
    painterRes: Int,
    description: String,
    isEnable: Boolean = true
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(Color.Transparent, RoundedCornerShape(cornerRadius))
            .clip(RoundedCornerShape(cornerRadius))
            then(
                if (isEnable) {
                    Modifier.clickable(
                        onClick = onClick,
                    )
                } else {
                    Modifier
                }
            )
            .border(1.dp, border, RoundedCornerShape(cornerRadius))
            .padding(4.dp)
    ) {
        Icon(
            painter = painterResource(painterRes),
            tint = if (isEnable) iconColor else border,
            contentDescription = description,
            modifier = Modifier.size(14.dp)
        )
    }
}