package com.lazy.pizza.history.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazy.pizza.core.domain.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val cartRepository: CartRepository,
): ViewModel() {

    private val _state = MutableStateFlow(HistoryState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            cartRepository.getCartFlow().collectLatest { cart ->
                _state.value = _state.value.copy(
                    cartAmount = cart.sumOf { it.amount }
                )
            }
        }
    }
}