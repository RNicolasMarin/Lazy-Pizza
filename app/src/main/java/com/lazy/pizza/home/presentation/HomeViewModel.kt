package com.lazy.pizza.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazy.pizza.core.domain.ProductRepository
import com.lazy.pizza.core.domain.ProductsByCategory
import com.lazy.pizza.core.domain.Result
import com.lazy.pizza.home.presentation.HomeAction.*
import com.lazy.pizza.home.presentation.HomeAction.ActionWithProduct.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ProductRepository
): ViewModel() {

    private val allProductsByCategory = MutableStateFlow<List<ProductsByCategory>>(emptyList())
    private val searchField = MutableStateFlow("")

    var state: StateFlow<HomeState> = combine(
        allProductsByCategory,
        searchField
    ) { allProducts, field ->
        val filtered = allProducts.mapNotNull {
            val filteredProducts = it.products.filter { product ->
                product.name.contains(field.trim(), ignoreCase = true)
            }
            if (filteredProducts.isNotEmpty()) {
                it.copy(products = filteredProducts)
            } else {
                null // exclude empty categories
            }
        }
        HomeState(
            searchField = field,
            productsByCategories = filtered
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeState()
    )

    init {
        viewModelScope.launch {
            val products = repository.getProducts()

            if (products is Result.Success) {
                allProductsByCategory.value = products.data
            }
        }
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is UpdateSearchBar -> {
                searchField.value = action.searching
            }

            is ActionWithProduct -> {
                allProductsByCategory.value = allProductsByCategory.value.map {
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
            }
        }
    }
}