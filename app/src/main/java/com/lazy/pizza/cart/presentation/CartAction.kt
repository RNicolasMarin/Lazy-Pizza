package com.lazy.pizza.cart.presentation

import com.lazy.pizza.core.domain.Product

interface CartAction {

    data object GoBackToMenu : CartAction

    data class DeleteFromCart(val productPosition: Int) : CartAction

    data class IncreaseFromCart(val productPosition: Int) : CartAction

    data class ReduceFromCart(val product: Product, val productPosition: Int) : CartAction

    data class AddRecommendationToCart(val product: Product) : CartAction

    data object GoToCheckout : CartAction

}