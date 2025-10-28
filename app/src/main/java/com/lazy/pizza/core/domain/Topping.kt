package com.lazy.pizza.core.domain

import kotlinx.serialization.Serializable

@Serializable
data class Topping(
    val id: Long,
    val name: String,
    val unitPrice: Double,
    val amount: Int,
    val imageName: String? = null
)
