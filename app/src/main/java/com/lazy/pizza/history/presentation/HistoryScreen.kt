package com.lazy.pizza.history.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
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
import com.lazy.pizza.core.presentation.components.NavItem
import com.lazy.pizza.core.presentation.components.NothingToShow
import com.lazy.pizza.core.presentation.components.RootComposable
import com.lazy.pizza.core.presentation.designsystem.BG
import com.lazy.pizza.core.presentation.designsystem.DimensHome
import com.lazy.pizza.core.presentation.designsystem.InstrumentSansMedium
import com.lazy.pizza.core.presentation.designsystem.ScreenConfiguration
import com.lazy.pizza.core.presentation.designsystem.TextPrimary
import com.lazy.pizza.core.presentation.designsystem.dimen
import com.lazy.pizza.core.presentation.designsystem.screenConfiguration
import com.lazy.pizza.core.presentation.designsystem.statusBarHeight
import org.koin.androidx.compose.koinViewModel

@Composable
fun HistoryScreenRoot(
    onBack: () -> Unit,
    selected: NavItem,
    onNavSelected: (NavItem) -> Unit,
    viewModel: HistoryViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    BackHandler {
        onBack()
    }

    HistoryScreen(
        selected = selected,
        state = state,
        onNavSelected = onNavSelected
    )
}

@Composable
fun HistoryScreen(
    selected: NavItem,
    onNavSelected: (NavItem) -> Unit,
    state: HistoryState,
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
        HistoryScreenContent(
            bottomPadding = innerPadding.calculateBottomPadding()
        )
    }
}

@Composable
fun HistoryScreenContent(
    bottomPadding: Dp,
    dimens: DimensHome = MaterialTheme.dimen.home,
    screenConfiguration: ScreenConfiguration = MaterialTheme.screenConfiguration,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BG)
            .padding(
                start = dimens.paddingHorizontal,
                end = dimens.paddingHorizontal,
                bottom = if (screenConfiguration == ScreenConfiguration.PHONE_PORTRAIT) bottomPadding else 0.dp
            )
    ) {
        TopBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 20.dp,
                )
        )

        Spacer(Modifier.height(120.dp))
        NothingToShow(
            titleRes = R.string.history_not_signed_title,
            descriptionRes = R.string.history_not_signed_description,
            buttonRes = R.string.history_not_signed_button,
            onClick = {

            }
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
            text = stringResource(R.string.history_title),
            style = InstrumentSansMedium.copy(
                fontSize = 16.sp,
                lineHeight = 22.sp
            ),
            color = TextPrimary
        )
    }
}