package com.lazy.pizza.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.lazy.pizza.core.domain.Category.PIZZA
import com.lazy.pizza.core.domain.Product
import com.lazy.pizza.core.presentation.components.ProductAction.*
import com.lazy.pizza.core.presentation.designsystem.InstrumentSansMedium
import com.lazy.pizza.core.presentation.designsystem.InstrumentSansRegularNormal
import com.lazy.pizza.core.presentation.designsystem.InstrumentSansSemiBold
import com.lazy.pizza.core.presentation.designsystem.SurfaceHigher
import com.lazy.pizza.core.presentation.designsystem.SurfaceHighest
import com.lazy.pizza.core.presentation.designsystem.TextPrimary
import com.lazy.pizza.core.presentation.designsystem.TextSecondary
import com.lazy.pizza.core.presentation.designsystem.Urls
import com.lazy.pizza.home.presentation.AddButton
import com.lazy.pizza.home.presentation.DeleteButton
import java.util.Locale

@Composable
fun ProductCard(
    product: Product,
    extraInfo: ProductCardExtraInfo,
    onAction: (ProductAction) -> Unit,
    modifier: Modifier = Modifier,
    isMinusEnable: Boolean = true
) {
    val density = LocalDensity.current
    Card(
        modifier = modifier
            .then(
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
        var heightDp by remember { mutableStateOf(0.dp) }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .then(
                        if (heightDp != 0.dp) {
                            Modifier.height(heightDp)
                        } else {
                            Modifier
                        }
                    )
                    .width(120.dp)
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
                    .then(
                        if (heightDp != 0.dp) {
                            Modifier.height(heightDp)
                        } else {
                            Modifier
                        }
                    )
                    .onGloballyPositioned { coordinates ->
                        // Get height in pixels
                        val columnHeight = with(density) {
                            coordinates.size.height.toFloat().toDp()
                        }
                        heightDp = max(columnHeight, 120.dp)
                    }
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

                val extra = when (extraInfo) {
                    ProductCardExtraInfo.INGREDIENTS -> {
                        product.ingredients
                    }
                    ProductCardExtraInfo.TOPPINGS -> {
                        product.toppings.joinToString("\n") { "${it.amount} x ${it.name}" }
                    }
                }

                Text(
                    text = extra,
                    maxLines = if (extraInfo == ProductCardExtraInfo.INGREDIENTS) 2 else Int.MAX_VALUE,
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
                                isMinusEnable = isMinusEnable,
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

enum class ProductCardExtraInfo {
    INGREDIENTS,
    TOPPINGS
}

sealed class ProductAction(open val product: Product) {

    data class AddToCart(override val product: Product) : ProductAction(product)

    data class DeleteFromCart(override val product: Product) : ProductAction(product)

    data class ReduceFromCart(override val product: Product) : ProductAction(product)

    data class IncreaseFromCart(override val product: Product) : ProductAction(product)

    data class ProductSelected(override val product: Product) : ProductAction(product)

}