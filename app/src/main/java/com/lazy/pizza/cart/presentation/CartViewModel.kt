package com.lazy.pizza.cart.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazy.pizza.core.domain.ProductsByCategory
import com.lazy.pizza.core.domain.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class CartViewModel(
    private val cartRepository: CartRepository,
): ViewModel() {

    private val recommended = MutableStateFlow<List<ProductsByCategory>>(emptyList())

    var state: StateFlow<CartState> = combine(
        cartRepository.getCartFlow(),
        recommended
    ) { cart, recommended ->
        CartState(
            products = cart,
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
        }
    }
}