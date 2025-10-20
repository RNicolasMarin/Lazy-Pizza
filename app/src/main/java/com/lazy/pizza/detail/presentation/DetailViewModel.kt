package com.lazy.pizza.detail.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazy.pizza.core.domain.Result
import com.lazy.pizza.core.domain.repository.ToppingRepository
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
            is DetailAction.SetProduct -> {
                state = state.copy(
                    product = action.product
                )
            }

            DetailAction.OnBackPressed -> Unit
        }
    }
}