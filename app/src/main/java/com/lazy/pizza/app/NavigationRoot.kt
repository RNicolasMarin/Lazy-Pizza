package com.lazy.pizza.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.lazy.pizza.core.domain.Product
import com.lazy.pizza.detail.presentation.DetailScreenRoot
import com.lazy.pizza.home.presentation.HomeScreenRoot
import com.lazy.pizza.core.presentation.ProductNavType
import kotlin.reflect.typeOf

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
                onProductSelected = { product ->
                    navController.navigate(Screen.Detail(product))
                }
            )
        }

        composable<Screen.Detail>(
            typeMap = mapOf(typeOf<Product>() to ProductNavType)
        ) { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.Detail>()
            DetailScreenRoot(product = args.product)
        }
    }

}