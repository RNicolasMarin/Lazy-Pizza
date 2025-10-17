package com.lazy.pizza.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lazy.pizza.R
import com.lazy.pizza.core.presentation.designsystem.BG
import com.lazy.pizza.core.presentation.designsystem.DimensHome
import com.lazy.pizza.core.presentation.designsystem.InstrumentSansBold
import com.lazy.pizza.core.presentation.designsystem.InstrumentSansRegularNormal
import com.lazy.pizza.core.presentation.designsystem.LazyPizzaTheme
import com.lazy.pizza.core.presentation.designsystem.MultiDevicePreview
import com.lazy.pizza.core.presentation.designsystem.TextPrimary
import com.lazy.pizza.core.presentation.designsystem.dimen
import com.lazy.pizza.core.presentation.designsystem.statusBarHeight

@Composable
fun HomeScreenRoot(

) {
    HomeScreen()
}

@Composable
fun HomeScreen(
    dimens: DimensHome = MaterialTheme.dimen.home,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BG)
    ) {
        Spacer(
            modifier = Modifier.height(statusBarHeight())
        )
        TopBar(
            modifier = Modifier.fillMaxWidth()
                .padding(
                    vertical = 20.dp,
                    horizontal = dimens.paddingHorizontal
                )
        )
    }
}

@Composable
fun TopBar(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_toolbar_pizza),
            tint = Color.Unspecified,
            contentDescription = "Pizza Icon",
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = stringResource(R.string.home_toolbar_text),
            style = InstrumentSansBold,
            color = TextPrimary
        )
        Spacer(Modifier.width(6.dp))
        Spacer(Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.ic_toolbar_phone),
            tint = Color.Unspecified,
            contentDescription = "Phone Icon",
            modifier = Modifier.size(14.dp)
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = stringResource(R.string.home_toolbar_phone_number),
            style = InstrumentSansRegularNormal,
            color = TextPrimary
        )
    }
}

@MultiDevicePreview
@Composable
private fun ScanHistoryScreenPreview() {
    LazyPizzaTheme {
        HomeScreen(
        )
    }
}