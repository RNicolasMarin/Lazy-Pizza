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

fun List<Topping>.hasSameContent(other: List<Topping>): Boolean {
    return size == other.size &&
        zip(other).all { (a, b) ->
            a.hasSameContent(b)
        }
}

fun Topping.hasSameContent(other: Topping): Boolean {
    return id == other.id &&
            name == other.name &&
            unitPrice == other.unitPrice &&
            amount == other.amount &&
            imageName == other.imageName
}
