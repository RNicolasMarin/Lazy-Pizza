package com.lazy.pizza.cart.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lazy.pizza.R
import com.lazy.pizza.core.presentation.components.NavItem
import com.lazy.pizza.core.presentation.components.RootComposable
import com.lazy.pizza.core.presentation.designsystem.BG
import com.lazy.pizza.core.presentation.designsystem.InstrumentSansMedium
import com.lazy.pizza.core.presentation.designsystem.TextPrimary
import com.lazy.pizza.core.presentation.designsystem.statusBarHeight
import org.koin.androidx.compose.koinViewModel

@Composable
fun CartScreenRoot(
    onBack: () -> Unit,
    selected: NavItem,
    onNavSelected: (NavItem) -> Unit,
    viewModel: CartViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BackHandler {
        onBack()
    }

    CartScreen(
        selected = selected,
        onNavSelected = onNavSelected,
        state = state
    )
}

@Composable
fun CartScreen(
    selected: NavItem,
    onNavSelected: (NavItem) -> Unit,
    state: CartState,
) {
    RootComposable(
        selected = selected,
        cartAmount = state.cartAmount,
        modifier = Modifier
            .fillMaxSize()
            .background(BG)
            .padding(
                top = statusBarHeight()
            )
            .padding(WindowInsets.navigationBars.asPaddingValues()),
        onClick = { navItem ->
            onNavSelected(navItem)
        }
    ) { innerPadding ->
        CartScreenContent(
            bottomPadding = innerPadding.calculateBottomPadding()
        )
    }
}

@Composable
fun CartScreenContent(bottomPadding: Dp) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BG)
    ) {
        TopBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 20.dp,
                )
        )
    }
}

@Composable
fun TopBar(
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.cart_title),
            style = InstrumentSansMedium.copy(
                fontSize = 16.sp,
                lineHeight = 22.sp
            ),
            color = TextPrimary
        )
    }
}