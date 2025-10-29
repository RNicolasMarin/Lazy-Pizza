package com.lazy.pizza.cart.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.lazy.pizza.R
import com.lazy.pizza.cart.presentation.CartAction.*
import com.lazy.pizza.core.domain.Product
import com.lazy.pizza.core.presentation.components.GradientButton
import com.lazy.pizza.core.presentation.components.GradientButtonWithOverlay
import com.lazy.pizza.core.presentation.components.NavItem
import com.lazy.pizza.core.presentation.components.NothingToShow
import com.lazy.pizza.core.presentation.components.PlusButton
import com.lazy.pizza.core.presentation.components.ProductAction
import com.lazy.pizza.core.presentation.components.ProductCard
import com.lazy.pizza.core.presentation.components.ProductCardExtraInfo.*
import com.lazy.pizza.core.presentation.components.RootComposable
import com.lazy.pizza.core.presentation.designsystem.BG
import com.lazy.pizza.core.presentation.designsystem.DimensGradientButton
import com.lazy.pizza.core.presentation.designsystem.DimensHome
import com.lazy.pizza.core.presentation.designsystem.InstrumentSansMedium
import com.lazy.pizza.core.presentation.designsystem.InstrumentSansRegularNormal
import com.lazy.pizza.core.presentation.designsystem.InstrumentSansSemiBold
import com.lazy.pizza.core.presentation.designsystem.Primary
import com.lazy.pizza.core.presentation.designsystem.ScreenConfiguration
import com.lazy.pizza.core.presentation.designsystem.ScreenConfiguration.*
import com.lazy.pizza.core.presentation.designsystem.SurfaceHigher
import com.lazy.pizza.core.presentation.designsystem.SurfaceHighest
import com.lazy.pizza.core.presentation.designsystem.TextPrimary
import com.lazy.pizza.core.presentation.designsystem.TextSecondary
import com.lazy.pizza.core.presentation.designsystem.Urls
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
    dimensGradientButton: DimensGradientButton = MaterialTheme.dimen.gradientButton,
    screenConfiguration: ScreenConfiguration = MaterialTheme.screenConfiguration,
) {
    val listState = rememberLazyListState()

    if (screenConfiguration == PHONE_PORTRAIT) {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxSize()
                .background(BG)
                .padding(
                    start = dimens.paddingHorizontal,
                    end = dimens.paddingHorizontal,
                    bottom = bottomPadding
                )
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize()
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
                    item {
                        Recommended(
                            state = state,
                            onAction = onAction,
                            horizontalPadding = 0.dp,
                            modifier = Modifier
                                .fillMaxWidth(),
                        )
                    }
                    item {
                        Spacer(Modifier.height(dimensGradientButton.addCartOverlay))
                    }
                }
            }
            if (state.products.isNotEmpty()) {
                ButtonWithOverlay(
                    state = state,
                    onAction = onAction
                )
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

                    Recommended(
                        state = state,
                        onAction = onAction,
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

@Composable
fun Recommended(
    state: CartState,
    onAction: (CartAction) -> Unit,
    modifier: Modifier = Modifier,
    horizontalPadding: Dp = 16.dp,
    verticalPadding: Dp = 16.dp,
    screenConfiguration: ScreenConfiguration = MaterialTheme.screenConfiguration
) {
    Column(
        modifier = modifier
            .then(
                if (screenConfiguration == TABLET_PORTRAIT) {
                    Modifier.background(
                        SurfaceHigher,
                        RoundedCornerShape(
                            topStart = 16.dp,
                            bottomStart = 16.dp
                        )
                    )
                } else {
                    Modifier
                }
            )
            .padding(horizontal = horizontalPadding, vertical = verticalPadding)
    ) {
        Text(
            text = stringResource(R.string.cart_recommendations).uppercase(),
            style = InstrumentSansSemiBold.copy(
                fontSize = 12.sp,
                lineHeight = 16.sp
            ),
            color = TextSecondary
        )
        Spacer(Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(
                items = state.recommended,
                key = { product -> product.id }
            ) {
                ProductRecommendationCard(
                    product = it,
                    onPlus = {
                        onAction(
                            AddRecommendationToCart(it)
                        )
                    },
                )
            }
        }

        if (screenConfiguration == TABLET_PORTRAIT) {
            Spacer(Modifier.height(20.dp))

            GradientButton(
                text = stringResource(R.string.cart_go_to_checkout, state.cardTotal),
                onClick = {
                    onAction(GoToCheckout)
                },
                horizontalPadding = 12.dp,
                verticalPadding = 12.dp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun ProductRecommendationCard(
    product: Product,
    onPlus: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = SurfaceHigher,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(
            modifier = Modifier
                .width(160.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(120.dp)
                    .width(160.dp)
                    .padding(start = 2.dp, top = 2.dp, end = 2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            SurfaceHighest,
                            RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                        )
                )

                Image(
                    painter = rememberAsyncImagePainter(Urls.getProductImageUrl(product)),
                    contentDescription = "Icon",
                    modifier = Modifier
                        .size(108.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = product.name,
                    style = InstrumentSansRegularNormal.copy(
                        fontSize = 16.sp,
                        lineHeight = 22.sp
                    ),
                    color = TextSecondary
                )

                Spacer(Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "$${product.unitPrice}",
                        style = InstrumentSansSemiBold.copy(
                            fontSize = 24.sp,
                            lineHeight = 28.sp
                        ),
                        color = TextPrimary
                    )
                    Spacer(Modifier.width(8.dp))
                    PlusButton(
                        onClick = onPlus,
                        iconColor = Primary
                    )
                }
            }
        }
    }
}

@Composable
fun ButtonWithOverlay(
    onAction: (CartAction) -> Unit,
    state: CartState,
    modifier: Modifier = Modifier
) {
    GradientButtonWithOverlay(
        text = stringResource(R.string.cart_go_to_checkout, state.cardTotal),
        onClick = {
            onAction(GoToCheckout)
        },
        modifier = modifier
    )
}