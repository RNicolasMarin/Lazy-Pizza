package com.lazy.pizza.detail.presentation

import com.lazy.pizza.core.domain.Product
import com.lazy.pizza.core.domain.Topping

sealed interface DetailAction {

    data object OnBackPressed: DetailAction

    data class SetProduct(val product: Product): DetailAction

    sealed class ActionAffectingToppingQuantity(open val topping: Topping) : DetailAction {

        data class AddToCart(override val topping: Topping) : ActionAffectingToppingQuantity(topping)

        data class ReduceFromCart(override val topping: Topping) : ActionAffectingToppingQuantity(topping)

        data class IncreaseFromCart(override val topping: Topping) : ActionAffectingToppingQuantity(topping)
    }

}