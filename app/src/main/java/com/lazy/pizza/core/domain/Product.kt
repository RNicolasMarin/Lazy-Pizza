package com.lazy.pizza.core.domain

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Long,
    val category: Category,
    val name: String,
    val ingredients: String,
    val unitPrice: Double,
    val amount: Int,
    val imageName: String? = null,
    val toppings: List<Topping> = emptyList()
)