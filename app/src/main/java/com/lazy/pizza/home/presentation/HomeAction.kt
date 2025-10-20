package com.lazy.pizza.home.presentation

import com.lazy.pizza.core.domain.Product

sealed interface HomeAction {

    data class UpdateSearchBar(val searching: String) : HomeAction

    sealed class ActionAffectingProductQuantity(open val product: Product) : HomeAction {

        data class AddToCart(override val product: Product) : ActionAffectingProductQuantity(product)

        data class DeleteFromCart(override val product: Product) : ActionAffectingProductQuantity(product)

        data class ReduceFromCart(override val product: Product) : ActionAffectingProductQuantity(product)

        data class IncreaseFromCart(override val product: Product) : ActionAffectingProductQuantity(product)
    }

    data class ProductSelected(val product: Product) : HomeAction

}