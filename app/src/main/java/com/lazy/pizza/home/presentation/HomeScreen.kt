package com.lazy.pizza.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lazy.pizza.R
import com.lazy.pizza.core.presentation.designsystem.BG
import com.lazy.pizza.core.presentation.designsystem.DimensHome
import com.lazy.pizza.core.presentation.designsystem.InstrumentSansBold
import com.lazy.pizza.core.presentation.designsystem.InstrumentSansRegularNormal
import com.lazy.pizza.core.presentation.designsystem.LazyPizzaTheme
import com.lazy.pizza.core.presentation.designsystem.MultiDevicePreview
import com.lazy.pizza.core.presentation.designsystem.ScreenConfiguration
import com.lazy.pizza.core.presentation.designsystem.SurfaceHigher
import com.lazy.pizza.core.presentation.designsystem.TextPrimary
import com.lazy.pizza.core.presentation.designsystem.TextSecondary
import com.lazy.pizza.core.presentation.designsystem.dimen
import com.lazy.pizza.core.presentation.designsystem.screenConfiguration
import com.lazy.pizza.core.presentation.designsystem.statusBarHeight

@Composable
fun HomeScreenRoot(

) {
    HomeScreen()
}

@Composable
fun HomeScreen(
    dimens: DimensHome = MaterialTheme.dimen.home,
    screenConfiguration: ScreenConfiguration = MaterialTheme.screenConfiguration,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BG)
            .padding(
                horizontal = dimens.paddingHorizontal
            )
    ) {
        Spacer(
            modifier = Modifier.height(statusBarHeight())
        )
        TopBar(
            modifier = Modifier.fillMaxWidth()
                .padding(
                    vertical = 20.dp,
                )
        )
        Image(
            painter = painterResource(id =
                when(screenConfiguration) {
                    ScreenConfiguration.PHONE_PORTRAIT -> R.mipmap.home_banner_phone
                    ScreenConfiguration.TABLET_PORTRAIT -> R.mipmap.home_banner_tablet
                }
            ),
            contentDescription = "Home Banner",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))
        SearchBar(
            value = "",
            onValueChange = {}
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

@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 28.dp,
    innerPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(SurfaceHigher, RoundedCornerShape(cornerRadius))
            .clip(RoundedCornerShape(cornerRadius))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(innerPadding)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_searchbar),
                tint = Color.Unspecified,
                contentDescription = "Search Icon",
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier.weight(1f)
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = stringResource(R.string.home_searchbar_hint),
                        style = InstrumentSansRegularNormal,
                        color = TextSecondary
                    )
                }

                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    singleLine = true,
                    textStyle = InstrumentSansRegularNormal.copy(
                        color = TextPrimary
                    ),
                    cursorBrush = SolidColor(TextPrimary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 0.dp) // Adjust if needed
                )
            }
        }
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