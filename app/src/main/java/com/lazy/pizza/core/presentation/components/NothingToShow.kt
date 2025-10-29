package com.lazy.pizza.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lazy.pizza.core.presentation.designsystem.InstrumentSansMedium
import com.lazy.pizza.core.presentation.designsystem.InstrumentSansRegularNormal
import com.lazy.pizza.core.presentation.designsystem.TextPrimary
import com.lazy.pizza.core.presentation.designsystem.TextSecondary

@Composable
fun NothingToShow(
    titleRes: Int,
    descriptionRes: Int,
    buttonRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(titleRes),
            style = InstrumentSansMedium.copy(
                fontSize = 24.sp,
                lineHeight = 28.sp
            ),
            color = TextPrimary
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = stringResource(descriptionRes),
            style = InstrumentSansRegularNormal.copy(
                fontSize = 14.sp,
                lineHeight = 18.sp
            ),
            color = TextSecondary
        )
        Spacer(Modifier.height(20.dp))
        GradientButton(
            text = stringResource(buttonRes),
            onClick = onClick,
            horizontalPadding = 24.dp,
            verticalPadding = 9.dp
        )
    }
}