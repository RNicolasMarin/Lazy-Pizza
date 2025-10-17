package com.lazy.pizza.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lazy.pizza.core.presentation.designsystem.BG
import com.lazy.pizza.core.presentation.designsystem.LazyPizzaTheme
import com.lazy.pizza.core.presentation.designsystem.MultiDevicePreview
import com.lazy.pizza.core.presentation.designsystem.statusBarHeight

@Composable
fun HomeScreenRoot(

) {
    HomeScreen()
}

@Composable
fun HomeScreen(

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BG)
    ) {
        Spacer(
            modifier = Modifier.height(statusBarHeight())
        )
    }
}

@MultiDevicePreview
@Composable
private fun ScanHistoryScreenPreview() {
    LazyPizzaTheme {
        HomeScreen(
        )
    }
}