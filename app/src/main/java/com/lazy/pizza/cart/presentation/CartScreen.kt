package com.lazy.pizza.cart.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
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
import com.lazy.pizza.cart.presentation.CartAction.*
import com.lazy.pizza.core.presentation.components.NavItem
import com.lazy.pizza.core.presentation.components.NothingToShow
import com.lazy.pizza.core.presentation.components.ProductAction
import com.lazy.pizza.core.presentation.components.ProductCard
import com.lazy.pizza.core.presentation.components.ProductCardExtraInfo.*
import com.lazy.pizza.core.presentation.components.RootComposable
import com.lazy.pizza.core.presentation.designsystem.BG
import com.lazy.pizza.core.presentation.designsystem.DimensHome
import com.lazy.pizza.core.presentation.designsystem.InstrumentSansMedium
import com.lazy.pizza.core.presentation.designsystem.ScreenConfiguration
import com.lazy.pizza.core.presentation.designsystem.ScreenConfiguration.*
import com.lazy.pizza.core.presentation.designsystem.TextPrimary
import com.lazy.pizza.core.presentation.designsystem.dimen
import com.lazy.pizza.core.presentation.designsystem.screenConfiguration
import com.lazy.pizza.core.presentation.designsystem.statusBarHeight
import org.koin.androidx.compose.koinViewModel

@Composable
fun CartScreenRoot(
    onBack: () -> Unit,
    onGoBackToMenu: () -> Unit,
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
        state = state,
        onAction = { action ->
            when (action) {
                is GoBackToMenu -> onGoBackToMenu()
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun CartScreen(
    selected: NavItem,
    onAction: (CartAction) -> Unit,
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
            state = state,
            onAction = onAction,
            bottomPadding = innerPadding.calculateBottomPadding()
        )
    }
}

@Composable
fun CartScreenContent(
    state: CartState,
    bottomPadding: Dp,
    onAction: (CartAction) -> Unit,
    dimens: DimensHome = MaterialTheme.dimen.home,
    screenConfiguration: ScreenConfiguration = MaterialTheme.screenConfiguration,
) {
    val listState = rememberLazyListState()

    if (screenConfiguration == PHONE_PORTRAIT) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .background(BG)
                .padding(
                    start = dimens.paddingHorizontal,
                    end = dimens.paddingHorizontal,
                    bottom = bottomPadding
                )
        ) {
            item {
                TopBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 20.dp,
                        )
                )
            }
            if (state.products.isEmpty()) {
                item {
                    Column {
                        Spacer(Modifier.height(120.dp))
                        NothingToShow(
                            titleRes = R.string.cart_empty_title,
                            descriptionRes = R.string.cart_empty_description,
                            buttonRes = R.string.cart_empty_button,
                            onClick = {
                                onAction(GoBackToMenu)
                            }
                        )
                    }
                }
            } else {
                itemsIndexed(
                    items = state.products,
                    key = { _, product -> product.uniqueIdentifier }
                ) { index, product ->
                    ProductCard(
                        product = product,
                        extraInfo = TOPPINGS,
                        onAction = { action ->
                            val cartAction = when (action) {
                                is ProductAction.DeleteFromCart -> DeleteFromCart(index)
                                is ProductAction.IncreaseFromCart -> IncreaseFromCart(index)
                                is ProductAction.ReduceFromCart -> ReduceFromCart(action.product, index)
                                else -> null
                            }
                            if (cartAction != null) {
                                onAction(cartAction)
                            }
                        },
                        isMinusEnable = product.amount > 1,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    } else {
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
            if (state.products.isEmpty()) {
                Column {
                    Spacer(Modifier.height(120.dp))
                    NothingToShow(
                        titleRes = R.string.cart_empty_title,
                        descriptionRes = R.string.cart_empty_description,
                        buttonRes = R.string.cart_empty_button,
                        onClick = {
                            onAction(GoBackToMenu)
                        }
                    )
                }
            } else {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(
                                start = dimens.paddingHorizontal,
                                end = dimens.paddingHorizontal,
                                bottom = bottomPadding
                            )
                    ) {
                        itemsIndexed(
                            items = state.products,
                            key = { _, product -> product.uniqueIdentifier }
                        ) { index, product ->
                            ProductCard(
                                product = product,
                                extraInfo = TOPPINGS,
                                onAction = { action ->
                                    val cartAction = when (action) {
                                        is ProductAction.DeleteFromCart -> DeleteFromCart(index)
                                        is ProductAction.IncreaseFromCart -> IncreaseFromCart(index)
                                        is ProductAction.ReduceFromCart -> ReduceFromCart(action.product, index)
                                        else -> null
                                    }
                                    if (cartAction != null) {
                                        onAction(cartAction)
                                    }
                                },
                                isMinusEnable = product.amount > 1,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }
        }
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