package com.lazy.pizza.home.presentation

import com.lazy.pizza.core.domain.ProductsByCategory

data class HomeState(
    val searchField: String = "",
    val isLoading: Boolean = false,
    val productsByCategory: List<ProductsByCategory> = emptyList()
)
