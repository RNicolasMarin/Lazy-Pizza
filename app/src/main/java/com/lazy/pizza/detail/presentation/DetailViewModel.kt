package com.lazy.pizza.detail.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazy.pizza.core.domain.Result
import com.lazy.pizza.core.domain.repository.ToppingRepository
import com.lazy.pizza.detail.presentation.DetailAction.*
import com.lazy.pizza.detail.presentation.DetailAction.ActionAffectingToppingQuantity.*
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: ToppingRepository
): ViewModel() {

    var state by mutableStateOf(DetailState())
        private set

    init {
        viewModelScope.launch {
            val toppings = repository.getToppings()

            if (toppings is Result.Success) {
                state = state.copy(
                    toppings = toppings.data
                )
            }
        }
    }
    fun onAction(action: DetailAction) {
        when (action) {
            is SetProduct -> {
                state = state.copy(
                    product = action.product
                )
            }

            is ActionAffectingToppingQuantity -> {
                state = state.copy(
                    toppings = state.toppings.map { topping ->
                        if (topping.id == action.topping.id) {
                            when (action) {
                                is AddToCart -> {
                                    topping.copy(
                                        amount = 1
                                    )
                                }
                                is IncreaseFromCart -> {
                                    topping.copy(
                                        amount = topping.amount + 1,
                                    )
                                }
                                is ReduceFromCart -> {
                                    topping.copy(
                                        amount = topping.amount - 1,
                                    )
                                }
                            }
                        } else {
                            topping
                        }
                    }
                )
            }

            OnBackPressed -> Unit
        }
    }
}