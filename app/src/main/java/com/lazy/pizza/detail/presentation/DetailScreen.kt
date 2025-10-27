package com.lazy.pizza.detail.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.lazy.pizza.R
import com.lazy.pizza.core.domain.Product
import com.lazy.pizza.core.presentation.designsystem.BG
import com.lazy.pizza.core.presentation.designsystem.DimensDetail
import com.lazy.pizza.core.presentation.designsystem.InstrumentSansRegularNormal
import com.lazy.pizza.core.presentation.designsystem.InstrumentSansSemiBold
import com.lazy.pizza.core.presentation.designsystem.SurfaceHigher
import com.lazy.pizza.core.presentation.designsystem.TextPrimary
import com.lazy.pizza.core.presentation.designsystem.TextSecondary
import com.lazy.pizza.core.presentation.designsystem.TextSecondary8
import com.lazy.pizza.core.presentation.designsystem.Urls
import com.lazy.pizza.core.presentation.designsystem.dimen
import com.lazy.pizza.core.presentation.designsystem.statusBarHeight
import com.lazy.pizza.detail.presentation.DetailAction.*
import org.koin.androidx.compose.koinViewModel
import androidx.core.graphics.toColorInt
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lazy.pizza.core.data.repository.ToppingRepositoryImpl
import com.lazy.pizza.core.domain.Category
import com.lazy.pizza.core.domain.Topping
import com.lazy.pizza.core.presentation.components.AmountSelector
import com.lazy.pizza.core.presentation.designsystem.LazyPizzaTheme
import com.lazy.pizza.core.presentation.designsystem.MultiDevicePreview
import com.lazy.pizza.core.presentation.designsystem.Outline
import com.lazy.pizza.core.presentation.designsystem.Primary
import com.lazy.pizza.core.presentation.designsystem.Primary8
import com.lazy.pizza.core.presentation.designsystem.PrimaryGradientEnd
import com.lazy.pizza.core.presentation.designsystem.PrimaryGradientStart
import com.lazy.pizza.core.presentation.designsystem.ScreenConfiguration
import com.lazy.pizza.core.presentation.designsystem.TextOnPrimary
import com.lazy.pizza.core.presentation.designsystem.screenConfiguration
import com.lazy.pizza.detail.presentation.DetailAction.ActionAffectingToppingQuantity.*
import java.util.Locale

@Composable
fun DetailScreenRoot(
    product: Product,
    onBackPressed: () -> Unit,
    viewModel: DetailViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.onAction(SetProduct(product))
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    DetailScreen(
        state = state,
        onAction = { action ->
            when (action) {
                OnBackPressed -> onBackPressed()
                else -> Unit
            }
            viewModel.onAction(action)
        },
    )
}

@Composable
fun DetailScreen(
    state: DetailState,
    onAction: (DetailAction) -> Unit,
    dimens: DimensDetail = MaterialTheme.dimen.detail,
    screenConfiguration: ScreenConfiguration = MaterialTheme.screenConfiguration
) {
    val listState = rememberLazyListState()

    val modifier = Modifier
        .fillMaxSize()
        .background(BG)
        .padding(
            top = statusBarHeight()
        )
        .padding(WindowInsets.navigationBars.asPaddingValues())

    if (screenConfiguration == ScreenConfiguration.PHONE_PORTRAIT) {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = modifier
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    CompleteBackButton(
                        onAction = onAction
                    )
                }
                item {
                    ProductImage(
                        state = state,
                        roundBottomEnd = true,
                    )
                }
                item {
                    ToppingsCard(
                        showNameAndIngredients = true,
                        state = state,
                        onAction = onAction,
                        modifier = Modifier
                            .fillMaxWidth()
                            .drawBehind {
                                val paint = Paint().asFrameworkPaint().apply {
                                    color = "#0A03131F".toColorInt()
                                    setShadowLayer(16f, 0f, -4f, color)
                                }
                                drawIntoCanvas {
                                    it.nativeCanvas.drawRect(0f, 0f, size.width, size.height, paint)
                                }
                            }
                    )
                }
            }
            ButtonWithOverlay(
                state = state
            )
        }
    } else {
        Column(
            modifier = modifier
        ) {
            CompleteBackButton(
                onAction = onAction
            )
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                val density = LocalDensity.current
                var totalHeight by remember { mutableStateOf(0.dp) }
                var cartHeight by remember { mutableStateOf(0.dp) }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(
                            start = dimens.paddingHorizontal,
                            end = dimens.paddingHorizontal,
                            bottom = 10.dp
                        )
                        .onGloballyPositioned { coordinates ->
                            // Get height in pixels
                            totalHeight = with(density) {
                                coordinates.size.height.toFloat().toDp()
                            }
                        }
                ) {
                    ProductImage(
                        state = state,
                        roundBottomEnd = false,
                    )
                    state.product?.let {
                        ProductNameAndIngredients(
                            product = it
                        )
                    }
                }
                if (totalHeight != 0.dp) {
                    Box(
                        contentAlignment = Alignment.BottomCenter,
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(max = min(cartHeight, totalHeight))
                    ) {
                        ToppingsCard(
                            bottomStart = 16.dp,
                            showNameAndIngredients = false,
                            state = state,
                            onAction = onAction,
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())//.heightIn(max = with(density) { totalHeightPx.toDp() } )
                                .onGloballyPositioned { coordinates ->
                                    // Get height in pixels
                                    cartHeight = with(density) {
                                        coordinates.size.height.toFloat().toDp()
                                    }
                                }
                        )
                        ButtonWithOverlay(
                            state = state
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CompleteBackButton(
    onAction: (DetailAction) -> Unit,
    dimens: DimensDetail = MaterialTheme.dimen.detail,
) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimens.paddingHorizontal,
                vertical = dimens.paddingHorizontal
            )
    ) {
        BackButton(
            onClick = {
                onAction(OnBackPressed)
            }
        )
    }
}

@Composable
fun ToppingsCard(
    showNameAndIngredients: Boolean,
    state: DetailState,
    onAction: (DetailAction) -> Unit,
    modifier: Modifier = Modifier,
    bottomStart: Dp = 0.dp,
    dimens: DimensDetail = MaterialTheme.dimen.detail,
) {
    Column(
        modifier = modifier
            .background(
                SurfaceHigher,
                RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 0.dp,
                    bottomStart = bottomStart,
                    bottomEnd = 0.dp
                )
            )
            .padding(
                start = dimens.paddingHorizontal,
                end = dimens.paddingHorizontal,
                bottom = 10.dp
            )
    ) {
        state.product?.let {
            if (showNameAndIngredients) {
                ProductNameAndIngredients(
                    product = it
                )
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.detail_add_toppings).uppercase(),
                style = InstrumentSansSemiBold.copy(
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                ),
                color = TextSecondary
            )

            Spacer(Modifier.height(6.dp))

            if (!state.isLoading) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .heightIn(max = 1000.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    userScrollEnabled = false // disable inner scroll so outer LazyColumn scrolls
                ) {
                    items(
                        items = state.toppings,
                        key = { topping -> topping.id }
                    ) { topping ->
                        ToppingCard(
                            onAction = onAction,
                            topping = topping
                        )
                    }
                    item {
                        Spacer(Modifier.height(dimens.addCartOverlay))
                    }
                }
            }
        }
    }
}

@Composable
fun ButtonWithOverlay(
    state: DetailState,
    modifier: Modifier = Modifier,
    dimens: DimensDetail = MaterialTheme.dimen.detail,
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = modifier
            .height(dimens.addCartOverlay)
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFFFFF),          // solid white at start
                        Color(0xFFFFFFFF).copy(alpha = 0f) // transparent at end
                    ),
                    startY = Float.POSITIVE_INFINITY, // 0deg → bottom to top
                    endY = 0f
                )
            )
            .padding(dimens.paddingHorizontal)
    ) {
        AddToCartButton(
            modifier = Modifier.fillMaxWidth(),
            price = state.cardTotal
        )
    }
}

@Composable
fun ColumnScope.ProductNameAndIngredients(
    product: Product
) {
    Spacer(Modifier.height(16.dp))
    Text(
        text = product.name,
        style = InstrumentSansSemiBold.copy(
            fontSize = 24.sp,
            lineHeight = 28.sp
        ),
        color = TextPrimary
    )
    Spacer(Modifier.height(4.dp))
    Text(
        text = product.ingredients,
        style = InstrumentSansRegularNormal.copy(
            fontSize = 14.sp,
            lineHeight = 18.sp
        ),
        color = TextSecondary
    )
}

@Composable
fun ProductImage(
    state: DetailState,
    roundBottomEnd : Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(SurfaceHigher)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    BG,
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 0.dp,
                        bottomEnd = if (roundBottomEnd) 16.dp else 0.dp
                    )
                )
        ) {
            state.product?.let {
                Image(
                    painter = rememberAsyncImagePainter(Urls.getProductImageUrl(state.product)),
                    contentDescription = "Icon",
                    modifier = Modifier
                        .size(240.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun ToppingCard(
    topping: Topping,
    onAction: (DetailAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .then(
                if (topping.amount == 0) {
                    Modifier.clickable(
                        onClick = {
                            onAction(AddToCart(topping))
                        }
                    )
                } else {
                    Modifier
                }
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = SurfaceHigher,
        ),
        border = BorderStroke(1.dp, if (topping.amount == 0) Outline else Primary),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(8.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(64.dp)
                    .background(Primary8, RoundedCornerShape(100.dp))
            ) {
                Image(
                    painter = rememberAsyncImagePainter(Urls.getToppingImageUrl(topping)),
                    contentDescription = "Icon",
                    modifier = Modifier
                        .size(56.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = topping.name,
                style = InstrumentSansRegularNormal.copy(
                    fontSize = 14.sp,
                    lineHeight = 18.sp
                ),
                color = TextSecondary
            )
            Spacer(Modifier.height(6.dp))

            if (topping.amount > 0) {
                AmountSelector(
                    modifier = Modifier.fillMaxWidth(),
                    amount = topping.amount.toString(),
                    onMinus = {
                        onAction(ReduceFromCart(topping))
                    },
                    onPlus = {
                        onAction(IncreaseFromCart(topping))
                    },
                    isPlusEnable = topping.amount < 3
                )
            } else {
                Text(
                    text = "$${topping.unitPrice.toPriceString()}",
                    style = InstrumentSansSemiBold.copy(
                        fontSize = 20.sp,
                        lineHeight = 24.sp
                    ),
                    color = TextPrimary
                )
            }
        }
    }
}

fun Double.toPriceString(): String {
    return if (this % 1.0 == 0.0) {
        this.toInt().toString()
    } else {
        String.format(Locale.US, "%.2f", this)
    }
}

@Composable
fun BackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 100.dp,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(TextSecondary8, RoundedCornerShape(cornerRadius))
            .clip(RoundedCornerShape(cornerRadius))
            .clickable(
                onClick = onClick,
            )
            .padding(8.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_back),
            tint = Color.Unspecified,
            contentDescription = "Back Button",
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
fun AddToCartButton(
    price: Double,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 100.dp
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .shadow(
                elevation = 6.dp,
                spotColor = Color(0x40F36B50), // #F36B5040 shadow
                shape = RoundedCornerShape(cornerRadius) // pill shape
            )
            .clip(RoundedCornerShape(cornerRadius))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        PrimaryGradientEnd, // right (start for 270deg)
                        PrimaryGradientStart // left
                    )
                )
            )
            .clickable(
                onClick = {}
            )
            .padding(12.dp)
    ) {
        Text(
            text = stringResource(R.string.detail_add_to_cart, price),
            style = InstrumentSansSemiBold.copy(
                fontSize = 15.sp,
                lineHeight = 22.sp
            ),
            color = TextOnPrimary
        )
    }
}

@MultiDevicePreview
@Composable
private fun DetailScreenPreview() {
    LazyPizzaTheme {
        DetailScreen(
            state = DetailState(
                toppings = ToppingRepositoryImpl.toppings,
                product = Product(
                    id = 1,
                    category = Category.PIZZA,
                    name = "Margherita",
                    ingredients = "Tomato sauce, mozzarella, fresh basil, olive oil",
                    unitPrice = 8.99,
                    amount = 0,
                )
            ),
            onAction = {},
        )
    }
}