package com.lazy.pizza.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lazy.pizza.R
import com.lazy.pizza.core.presentation.designsystem.InstrumentSansSemiBold
import com.lazy.pizza.core.presentation.designsystem.Outline
import com.lazy.pizza.core.presentation.designsystem.TextPrimary
import com.lazy.pizza.core.presentation.designsystem.TextSecondary

@Composable
fun AmountSelector(
    amount: String,
    onMinus: () -> Unit,
    onPlus: () -> Unit,
    modifier: Modifier = Modifier,
    isPlusEnable: Boolean = true,
    isMinusEnable: Boolean = true
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        MinusButton(
            isEnable = isMinusEnable,
            onClick = onMinus
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = amount,
            style = InstrumentSansSemiBold.copy(
                fontSize = 20.sp,
                lineHeight = 24.sp
            ),
            color = TextPrimary
        )
        Spacer(Modifier.width(8.dp))
        PlusButton(
            isEnable = isPlusEnable,
            onClick = onPlus
        )
    }
}

@Composable
fun MinusButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnable: Boolean = true
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        iconColor = TextSecondary,
        border = Outline,
        painterRes = R.drawable.ic_minus,
        description = "Minus Icon",
        isEnable = isEnable
    )
}

@Composable
fun PlusButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnable: Boolean = true,
    iconColor: Color = TextSecondary,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        iconColor = iconColor,
        border = Outline,
        painterRes = R.drawable.ic_plus,
        description = "Plus Icon",
        isEnable = isEnable
    )
}