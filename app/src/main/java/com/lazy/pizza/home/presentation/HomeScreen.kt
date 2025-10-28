package com.lazy.pizza.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.lazy.pizza.R
import com.lazy.pizza.core.data.repository.ProductRepositoryImpl
import com.lazy.pizza.core.domain.Category
import com.lazy.pizza.core.domain.Category.*
import com.lazy.pizza.core.domain.Product
import com.lazy.pizza.core.presentation.components.AmountSelector
import com.lazy.pizza.core.presentation.components.IconButton
import com.lazy.pizza.core.presentation.components.RootComposable
import com.lazy.pizza.core.presentation.designsystem.BG
import com.lazy.pizza.core.presentation.designsystem.DimensHome
import com.lazy.pizza.core.presentation.designsystem.InstrumentSansBold
import com.lazy.pizza.core.presentation.designsystem.InstrumentSansMedium
import com.lazy.pizza.core.presentation.designsystem.InstrumentSansRegularNormal
import com.lazy.pizza.core.presentation.designsystem.InstrumentSansSemiBold
import com.lazy.pizza.core.presentation.designsystem.LazyPizzaTheme
import com.lazy.pizza.core.presentation.designsystem.MultiDevicePreview
import com.lazy.pizza.core.presentation.designsystem.ObserveAsEvents
import com.lazy.pizza.core.presentation.designsystem.Outline
import com.lazy.pizza.core.presentation.designsystem.Outline50
import com.lazy.pizza.core.presentation.designsystem.Primary
import com.lazy.pizza.core.presentation.designsystem.Primary8
import com.lazy.pizza.core.presentation.designsystem.ScreenConfiguration
import com.lazy.pizza.core.presentation.designsystem.SurfaceHigher
import com.lazy.pizza.core.presentation.designsystem.SurfaceHighest
import com.lazy.pizza.core.presentation.designsystem.TextPrimary
import com.lazy.pizza.core.presentation.designsystem.TextSecondary
import com.lazy.pizza.core.presentation.designsystem.Urls
import com.lazy.pizza.core.presentation.designsystem.dimen
import com.lazy.pizza.core.presentation.designsystem.screenConfiguration
import com.lazy.pizza.core.presentation.designsystem.statusBarHeight
import com.lazy.pizza.home.presentation.HomeAction.*
import com.lazy.pizza.home.presentation.HomeAction.ActionAffectingProductQuantity.*
import com.lazy.pizza.home.presentation.HomeEvent.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@Composable
fun HomeScreenRoot(
    onProductSelected: (Product) -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {

    var snackBarMessage by remember { mutableStateOf<String?>(null) }

    val snackBarHostState = remember { SnackbarHostState() }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ProductAddedToCart -> {
                snackBarMessage = "${event.product.name} added to cart"
            }
        }
    }

    LaunchedEffect(snackBarMessage) {
        snackBarMessage?.let {
            snackBarHostState.showSnackbar(
                message = it,
            )
            delay(100)
            snackBarMessage = null
        }
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        hostState = snackBarHostState,
        state = state,
        onAction = { action ->
            when (action) {
                is ProductSelected -> {
                    onProductSelected(action.product)
                }
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun HomeScreen(
    hostState: SnackbarHostState,
    state: HomeState,
    onAction: (HomeAction) -> Unit,
) {
    RootComposable(
        hostState = hostState,
        cartAmount = state.cartAmount,
        modifier = Modifier
            .fillMaxSize()
            .background(BG)
            .padding(
                top = statusBarHeight()
            )
            .padding(WindowInsets.navigationBars.asPaddingValues()),
        onClick = { navItem ->

        }
    ) { innerPadding ->
        HomeScreenContent(
            state = state,
            onAction = onAction,
            bottomPadding = innerPadding.calculateBottomPadding()
        )
    }
}

@Composable
fun HomeScreenContent(
    bottomPadding: Dp,
    state: HomeState,
    onAction: (HomeAction) -> Unit,
    dimens: DimensHome = MaterialTheme.dimen.home,
    screenConfiguration: ScreenConfiguration = MaterialTheme.screenConfiguration,
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val density = LocalDensity.current
    var totalHeightPx by remember { mutableFloatStateOf(0f) }
    var beforeProductsHeightPx by remember { mutableFloatStateOf(0f) }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .background(BG)
            .padding(
                start = dimens.paddingHorizontal,
                end = dimens.paddingHorizontal,
                bottom = if (screenConfiguration == ScreenConfiguration.PHONE_PORTRAIT) bottomPadding else 0.dp
            )
            .onGloballyPositioned { coordinates ->
                // Get height in pixels
                totalHeightPx = coordinates.size.height.toFloat()
            }
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        // Get height in pixels
                        beforeProductsHeightPx = coordinates.size.height.toFloat()
                    }
            ) {
                TopBar(
                    modifier = Modifier
                        .fillMaxWidth()
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
                    value = state.searchField,
                    onValueChange = {
                        onAction(UpdateSearchBar(it))
                    }
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                ) {
                    items(
                        items = Category.entries.toList(),
                        key = { category -> category }
                    ) { category ->
                        CategoryButton(
                            category = stringResource(when (category) {
                                PIZZA -> R.string.home_category_pizza
                                DRINKS -> R.string.home_category_drinks
                                SAUCES -> R.string.home_category_sauces
                                ICE_CREAM -> R.string.home_category_ice_cream
                            }),
                            onClick = {
                                scope.launch {
                                    // Scroll to "Vegetables" header, which is at index 1 + fruits.size
                                    var index = 1//for the header
                                    var categoryShown = false
                                    for (productsByCategory in state.productsByCategories) {
                                        if (productsByCategory.category == category) {
                                            categoryShown = true
                                            break
                                        }
                                        index += 3
                                    }

                                    if (categoryShown) {
                                        listState.animateScrollToItem(index)
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }

        if (!state.isLoading) {
            if (state.productsByCategories.isEmpty()) {
                val noProductsHeightDp = with(density) {
                    (totalHeightPx - beforeProductsHeightPx).toDp()
                }
                item {
                    Box(
                        modifier = Modifier
                            .height(noProductsHeightDp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.home_no_products),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray
                        )
                    }
                }
            } else {
                state.productsByCategories.forEach { (category, products) ->
                    item {
                        val text = when(category) {
                            PIZZA -> R.string.home_products_category_pizza
                            DRINKS -> R.string.home_products_category_drinks
                            SAUCES -> R.string.home_products_category_sauces
                            ICE_CREAM -> R.string.home_products_category_ice_cream
                        }
                        Text(
                            text = stringResource(text),
                            style = InstrumentSansSemiBold,
                            color = TextSecondary
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                    item {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(dimens.columnsAmount),
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 3000.dp), // must define a height or it won't render
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            userScrollEnabled = false // disable inner scroll so outer LazyColumn scrolls
                        ) {

                            this@LazyVerticalGrid.items(
                                items = products,
                                key = { product -> product.id }
                            ) { product ->
                                ProductCard(
                                    onAction = onAction,
                                    product = product,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                    item {
                        Spacer(Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ProductCard(
    onAction: (HomeAction) -> Unit,
    product: Product,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            then(
                if (product.category == PIZZA) {
                    Modifier.clickable(
                        onClick = {
                            onAction(ProductSelected(product))
                        }
                    )
                } else {
                    Modifier
                }
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = SurfaceHigher,
        )
    ) {
        Row(
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(120.dp)
                    .padding(start = 2.dp, top = 2.dp, bottom = 2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            SurfaceHighest,
                            RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
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
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(
                        vertical = 12.dp,
                        horizontal = 16.dp
                    )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = product.name,
                        style = InstrumentSansMedium.copy(
                            fontSize = 16.sp,
                            lineHeight = 22.sp
                        ),
                        color = TextPrimary,
                        modifier = Modifier.weight(1f)
                    )
                    if (product.amount > 0) {
                        Spacer(Modifier.width(4.dp))
                        DeleteButton(
                            onClick = {
                                onAction(DeleteFromCart(product))
                            }
                        )
                    }
                }

                Text(
                    text = product.ingredients,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = InstrumentSansRegularNormal.copy(
                        fontSize = 14.sp,
                        lineHeight = 18.sp
                    ),
                    color = TextSecondary
                )

                Spacer(Modifier.weight(1f))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    when {
                        product.amount == 0 -> {
                            Text(
                                text = "$${product.unitPrice}",
                                style = InstrumentSansSemiBold.copy(
                                    fontSize = 24.sp,
                                    lineHeight = 28.sp
                                ),
                                color = TextPrimary
                            )
                            if (product.category != PIZZA) {
                                Spacer(Modifier.weight(1f))
                                AddButton(
                                    onClick = {
                                        onAction(AddToCart(product))
                                    }
                                )
                            }
                        }
                        product.amount > 0 -> {
                            AmountSelector(
                                amount = product.amount.toString(),
                                onMinus = {
                                    onAction(ReduceFromCart(product))
                                },
                                onPlus = {
                                    onAction(IncreaseFromCart(product))
                                }
                            )

                            Spacer(Modifier.weight(1f))

                            Column(
                                horizontalAlignment = Alignment.End,
                            ) {
                                Text(
                                    text = "$${String.format(Locale.US, "%.2f", (product.amount * product.unitPrice))}",
                                    style = InstrumentSansSemiBold.copy(
                                        fontSize = 24.sp,
                                        lineHeight = 28.sp
                                    ),
                                    color = TextPrimary
                                )
                                Text(
                                    text = "${product.amount} x $${product.unitPrice}",
                                    style = InstrumentSansRegularNormal.copy(
                                        fontSize = 12.sp,
                                        lineHeight = 16.sp
                                    ),
                                    color = TextSecondary
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun DeleteButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        iconColor = Primary,
        border = Outline50,
        painterRes = R.drawable.ic_delete,
        description = "Delete Icon"
    )
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

@Composable
fun CategoryButton(
    category: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlineButtonText(
        modifier = modifier,
        text = category,
        onClick = onClick,
        cornerRadius = 8.dp,
        border = Outline,
        horizontalPadding = 12.dp,
        verticalPadding = 6.dp,
        textColor = TextPrimary,
        textStyle = InstrumentSansMedium
    )
}

@Composable
fun AddButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlineButtonText(
        modifier = modifier,
        text = stringResource(R.string.home_products_add),
        onClick = onClick,
        cornerRadius = 100.dp,
        border = Primary8,
        horizontalPadding = 12.dp,
        verticalPadding = 6.dp,
        textColor = Primary,
        textStyle = InstrumentSansSemiBold
    )
}

@Composable
fun OutlineButtonText(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    cornerRadius: Dp,
    border: Color,
    horizontalPadding: Dp,
    verticalPadding: Dp,
    textColor: Color,
    textStyle: TextStyle
) {
    Box(
        modifier = modifier
            .background(Color.Transparent, RoundedCornerShape(cornerRadius))
            .clip(RoundedCornerShape(cornerRadius))
            .clickable(
                onClick = onClick,
            )
            .border(1.dp, border, RoundedCornerShape(cornerRadius))
            .padding(horizontal = horizontalPadding, vertical = verticalPadding)
    ) {
        Text(
            text = text,
            style = textStyle,
            color = textColor
        )
    }
}

@MultiDevicePreview
@Composable
private fun ScanHistoryScreenPreview() {
    LazyPizzaTheme {
        HomeScreen(
            hostState = SnackbarHostState(),
            state = HomeState(
                productsByCategories = ProductRepositoryImpl.productsByCategory
            ),
            onAction = {},
        )
    }
}