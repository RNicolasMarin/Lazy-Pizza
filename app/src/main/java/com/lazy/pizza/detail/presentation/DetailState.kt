package com.lazy.pizza.detail.presentation

import com.lazy.pizza.core.domain.Product
import com.lazy.pizza.core.domain.Topping

data class DetailState(
    val product: Product? = null,
    val isLoading: Boolean = false,
    val cardTotal: Double = 0.0,
    val toppings: List<Topping> = emptyList()
)
