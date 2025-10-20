package com.lazy.pizza.home.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazy.pizza.core.domain.ProductRepository
import com.lazy.pizza.core.domain.Result
import com.lazy.pizza.home.presentation.HomeAction.*
import com.lazy.pizza.home.presentation.HomeAction.ActionWithProduct.*
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

            is ActionWithProduct -> {
                state = state.copy(
                    productsByCategory = state.productsByCategory.map {
                        if (it.category == action.product.category) {
                            it.copy(
                                products = it.products.map { product ->
                                    if (product.id == action.product.id) {
                                        when (action) {
                                            is AddToCart -> {
                                                product.copy(
                                                    amount = 1
                                                )
                                            }
                                            is DeleteFromCart -> {
                                                product.copy(
                                                    amount = 0,
                                                )
                                            }
                                            is IncreaseFromCart -> {
                                                product.copy(
                                                    amount = product.amount + 1,
                                                )
                                            }
                                            is ReduceFromCart -> {
                                                product.copy(
                                                    amount = product.amount - 1,
                                                )
                                            }
                                        }
                                    } else {
                                        product
                                    }
                                }
                            )
                        } else {
                            it
                        }
                    }
                )
            }
        }
    }
}