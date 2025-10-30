package com.lazy.pizza.core.domain

data class ProductsByCategory(
    val category: Category = Category.PIZZA,
    val products: List<Product> = emptyList()
)
