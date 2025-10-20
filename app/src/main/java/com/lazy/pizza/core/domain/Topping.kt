package com.lazy.pizza.core.domain

data class Topping(
    val id: Long,
    val name: String,
    val unitPrice: Double,
    val amount: Int,
    val imageName: String? = null
)
