package com.lazy.pizza.home.presentation

import com.lazy.pizza.core.domain.Product

sealed interface HomeAction {

    data class UpdateSearchBar(val searching: String) : HomeAction

    sealed class ActionWithProduct(open val product: Product) : HomeAction {

        data class AddToCart(override val product: Product) : ActionWithProduct(product)

        data class DeleteFromCart(override val product: Product) : ActionWithProduct(product)

        data class ReduceFromCart(override val product: Product) : ActionWithProduct(product)

        data class IncreaseFromCart(override val product: Product) : ActionWithProduct(product)
    }
}