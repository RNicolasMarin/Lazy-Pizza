package com.lazy.pizza.cart.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazy.pizza.core.domain.repository.CartRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class CartViewModel(
    private val cartRepository: CartRepository,
): ViewModel() {

    var state: StateFlow<CartState> = combine(
        cartRepository.getCartFlow(),
        cartRepository.getRecommendedFlow()
    ) { cart, recommended ->
        CartState(
            products = cart,
            recommended = recommended,
            cardTotal = cart.sumOf {
                it.unitPrice * it.amount +
                    it.toppings.sumOf { topping ->
                        topping.amount * topping.unitPrice
                    }
            },
            cartAmount = cart.sumOf { it.amount }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CartState()
    )

    fun onAction(action: CartAction) {
        when (action) {
            is CartAction.DeleteFromCart -> {
                cartRepository.removeProductFromCart(action.productPosition)
            }
            is CartAction.IncreaseFromCart -> {
                cartRepository.increaseProductQuantity(action.productPosition)
            }
            is CartAction.ReduceFromCart -> {
                if (action.product.amount == 1) {
                    cartRepository.removeProductFromCart(action.productPosition)
                } else {
                    cartRepository.decreaseProductQuantity(action.productPosition)
                }
            }
            is CartAction.AddRecommendationToCart -> {
                cartRepository.addProductToCart(
                    action.product.copy(amount = 1)
                )
            }
        }
    }
}