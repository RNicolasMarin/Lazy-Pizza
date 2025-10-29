package com.lazy.pizza.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.lazy.pizza.cart.presentation.CartScreenRoot
import com.lazy.pizza.core.domain.Product
import com.lazy.pizza.detail.presentation.DetailScreenRoot
import com.lazy.pizza.home.presentation.HomeScreenRoot
import com.lazy.pizza.core.presentation.ProductNavType
import com.lazy.pizza.core.presentation.components.ItemCart
import com.lazy.pizza.core.presentation.components.ItemMenu
import com.lazy.pizza.core.presentation.components.NavItem
import kotlin.reflect.typeOf

@Composable
fun NavigationRoot(
    onBack: () -> Unit,
    navController: NavHostController
) {
    var selected by remember { mutableStateOf(ItemMenu) }

    val onNavSelected: (NavItem) -> Unit = {
        val route = when (it) {
            ItemMenu -> Screen.Home
            ItemCart -> Screen.Cart
            else -> Screen.Home
            //ItemHistory -> Screen.Home
        }
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }


    NavHost(
        navController = navController,
        startDestination = Screen.Home
    ) {
        composable<Screen.Home> {
            HomeScreenRoot(
                selected = selected,
                onNavSelected = onNavSelected,
                onProductSelected = { product ->
                    navController.navigate(Screen.Detail(product))
                }
            )
        }

        composable<Screen.Detail>(
            typeMap = mapOf(typeOf<Product>() to ProductNavType)
        ) { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.Detail>()
            DetailScreenRoot(
                onBackPressed = {
                    navController.popBackStack()
                },
                product = args.product
            )
        }

        composable<Screen.Cart> {
            CartScreenRoot(
                selected = selected,
                onNavSelected = onNavSelected,
                onBack = {
                    onBack()
                }
            )
        }
    }

}