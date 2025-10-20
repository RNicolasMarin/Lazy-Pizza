package com.lazy.pizza.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lazy.pizza.detail.presentation.DetailScreenRoot
import com.lazy.pizza.home.presentation.HomeScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home
    ) {
        composable<Screen.Home> {
            HomeScreenRoot(
                onProductSelected = {
                    navController.navigate(
                        Screen.Detail
                    )
                }
            )
        }
        composable<Screen.Detail> {
            DetailScreenRoot()
        }
    }

}