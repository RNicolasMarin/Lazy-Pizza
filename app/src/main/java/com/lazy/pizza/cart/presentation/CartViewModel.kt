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
        recommended,
        cartRepository.getCartFlow()
    ) { recommended, cart ->
        CartState(
            products = cart,
            cartAmount = cart.sumOf { it.amount }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CartState()
    )


}