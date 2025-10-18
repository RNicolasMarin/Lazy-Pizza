package com.lazy.pizza.core.domain

data class Product(
    val id: Long,
    val category: Category,
    val name: String,
    val description: String,
    val unitPrice: Double,
    val amount: Int,
    val imageName: String? = null
)
