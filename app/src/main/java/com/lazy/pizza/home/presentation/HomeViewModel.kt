package com.lazy.pizza.home.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazy.pizza.core.domain.ProductRepository
import com.lazy.pizza.core.domain.Result
import com.lazy.pizza.home.presentation.HomeAction.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ProductRepository
): ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    init {
        viewModelScope.launch {
            val products = repository.getProducts()

            if (products is Result.Success) {
                state = state.copy(
                    productsByCategory = products.data
                )
            }
        }

    }

    fun onAction(action: HomeAction) {
        when (action) {
            is UpdateSearchBar -> {
                state = state.copy(
                    searchField = action.searching
                )
            }
        }
    }
}