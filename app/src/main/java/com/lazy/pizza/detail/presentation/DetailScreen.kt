package com.lazy.pizza.detail.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
import com.lazy.pizza.core.data.repository.ToppingRepositoryImpl
import com.lazy.pizza.core.domain.Category
import com.lazy.pizza.core.domain.Topping
import com.lazy.pizza.core.presentation.designsystem.LazyPizzaTheme
import com.lazy.pizza.core.presentation.designsystem.MultiDevicePreview
import com.lazy.pizza.core.presentation.designsystem.Outline
import com.lazy.pizza.core.presentation.designsystem.Primary8
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
    DetailScreen(
        state = viewModel.state,
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
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BG)
            .padding(
                top = statusBarHeight()
            )
            .padding(WindowInsets.navigationBars.asPaddingValues())
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceHigher)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BG, RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 0.dp, bottomEnd = 16.dp))
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .drawBehind {
                    val paint = Paint().asFrameworkPaint().apply {
                        color = "#0A03131F".toColorInt()
                        setShadowLayer(16f, 0f, -4f, color)
                    }
                    drawIntoCanvas {
                        it.nativeCanvas.drawRect(0f, 0f, size.width, size.height, paint)
                    }
                }
                .background(SurfaceHigher, RoundedCornerShape(topStart = 16.dp, topEnd = 0.dp, bottomStart = 0.dp, bottomEnd = 0.dp))
                .padding(
                    start = dimens.paddingHorizontal,
                    end = dimens.paddingHorizontal,
                    top = 20.dp,
                    bottom = 10.dp
                )
        ) {
            state.product?.let {
                Text(
                    text = it.name,
                    style = InstrumentSansSemiBold.copy(
                        fontSize = 24.sp,
                        lineHeight = 28.sp
                    ),
                    color = TextPrimary
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = it.ingredients,
                    style = InstrumentSansRegularNormal.copy(
                        fontSize = 14.sp,
                        lineHeight = 18.sp
                    ),
                    color = TextSecondary
                )
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
                        //state = listState,
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(
                            items = state.toppings,
                            key = { topping -> topping.id }
                        ) { topping ->
                            ToppingCard(
                                topping = topping
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ToppingCard(
    topping: Topping,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = SurfaceHigher,
        ),
        border = BorderStroke(1.dp, Outline),
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