package com.lazy.pizza.core.domain

data class ProductsByCategory(
    val category: Category,
    val products: List<Product>
)
