package com.lazy.pizza.detail.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
                        painter = rememberAsyncImagePainter(Urls.getImageUrl(state.product)),
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
                .background(SurfaceHigher, RoundedCornerShape(topStart = 16.dp, topEnd = 0.dp, bottomStart = 0.dp, bottomEnd = 0.dp))
                .padding(
                    horizontal = dimens.paddingHorizontal,
                    vertical = 20.dp
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
            }
        }
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