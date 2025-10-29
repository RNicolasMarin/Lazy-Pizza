package com.lazy.pizza.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lazy.pizza.R
import com.lazy.pizza.core.presentation.designsystem.BG
import com.lazy.pizza.core.presentation.designsystem.DimensNav
import com.lazy.pizza.core.presentation.designsystem.InstrumentSansMedium
import com.lazy.pizza.core.presentation.designsystem.Outline
import com.lazy.pizza.core.presentation.designsystem.Primary
import com.lazy.pizza.core.presentation.designsystem.Primary8
import com.lazy.pizza.core.presentation.designsystem.ScreenConfiguration
import com.lazy.pizza.core.presentation.designsystem.SurfaceHigher
import com.lazy.pizza.core.presentation.designsystem.TextOnPrimary
import com.lazy.pizza.core.presentation.designsystem.TextSecondary
import com.lazy.pizza.core.presentation.designsystem.dimen
import com.lazy.pizza.core.presentation.designsystem.screenConfiguration

val ItemMenu = NavItem(R.string.nav_menu, R.drawable.ic_menu_menu)
val ItemCart = NavItem(R.string.nav_cart, R.drawable.ic_menu_cart)
val ItemHistory = NavItem(R.string.nav_history, R.drawable.ic_menu_history)

val navItems = listOf(ItemMenu, ItemCart, ItemHistory)

@Composable
fun RootComposable(
    selected: NavItem,
    cartAmount: Int,
    onClick: (NavItem) -> Unit,
    modifier: Modifier = Modifier,
    hostState: SnackbarHostState = SnackbarHostState(),
    screenConfiguration: ScreenConfiguration = MaterialTheme.screenConfiguration,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = hostState) },
        bottomBar = {
            if (screenConfiguration == ScreenConfiguration.PHONE_PORTRAIT) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 8.dp,
                        )
                        .background(SurfaceHigher, RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                    horizontalArrangement = Arrangement.Center, // Center horizontally
                ) {
                    navItems.forEachIndexed { index, item ->
                        if (index > 0) Spacer(modifier = Modifier.width(8.dp)) // Add 8.dp between items

                        NavItemComponent(
                            item = item,
                            selected = item == selected,
                            onClick = {
                                onClick(item)
                            },
                            cartAmount = if (item == ItemCart) cartAmount else 0,
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Row(
            modifier = Modifier
        ) {
            if (screenConfiguration == ScreenConfiguration.TABLET_PORTRAIT) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .shadow(
                            elevation = 8.dp,
                        )
                        .background(BG)
                        .padding(horizontal = 12.dp)
                ) {
                    navItems.forEachIndexed { index, item ->
                        NavItemComponent(
                            item = item,
                            selected = item == selected,
                            onClick = {
                                onClick(item)
                            },
                            cartAmount = if (item == ItemCart) cartAmount else 0,
                        )
                    }
                }
            }
            VerticalDivider(
                modifier = Modifier.fillMaxHeight(),
                thickness = 1.dp,
                color = Outline
            )
            Box(
                modifier = Modifier.weight(1f)
            ) {
                content(innerPadding)
            }
        }
    }
}

@Composable
fun NavItemComponent(
    cartAmount: Int,
    item: NavItem,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    dimens: DimensNav = MaterialTheme.dimen.nav,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable(
                onClick = onClick
            )
            .padding(top = 4.dp, start = dimens.horizontalIconPadding, end = dimens.horizontalIconPadding, bottom = dimens.bottomIconPadding)
    ) {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.width(dimens.cardAndNumberWidth)
        ) {
            Row(
                modifier = Modifier.width(dimens.cardAndNumberWidth)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                CartNumber(
                    number = cartAmount
                )
            }
            Column {
                Spacer(modifier = Modifier.height(dimens.spaceBeforeNumberHeight))
                IconBorder(
                    item = item,
                    selected = selected,
                )
            }
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = stringResource(item.labelRes),
            style = InstrumentSansMedium.copy(
                fontSize = 11.sp,
                lineHeight = 16.sp
            ),
            color = TextSecondary
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun CartNumber(
    number: Int,
    modifier: Modifier = Modifier
) {
    if (number != 0) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .size(16.dp)
                .background(Primary, RoundedCornerShape(100.dp))
        ) {
            Text(
                text = number.toString(),
                style = InstrumentSansMedium.copy(
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                ),
                color = TextOnPrimary
            )
        }
    } else {
        Box(
            modifier = modifier.size(16.dp)
        )
    }
}

@Composable
fun IconBorder(
    item: NavItem,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(28.dp)
            .then(
                if (selected) {
                    Modifier.background(Primary8, RoundedCornerShape(100.dp))
                } else {
                    Modifier
                }
            )
    ) {
        Icon(
            painter = painterResource(item.iconRes),
            contentDescription = stringResource(item.labelRes),
            tint = if (selected) Primary else TextSecondary,
            modifier = Modifier.size(16.dp)
        )
    }
}

data class NavItem(val labelRes: Int, val iconRes: Int)