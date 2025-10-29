package com.lazy.pizza.cart.presentation

import com.lazy.pizza.core.domain.Product

data class CartState(
    val cartAmount: Int = 0,
    val cardTotal: Double = 0.0,
    val products: List<Product> = emptyList(),
    val recommended: List<Product> = emptyList(),
)
