package com.lazy.pizza.core.domain

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Long = 0L,
    val category: Category = Category.PIZZA,
    val name: String = "",
    val ingredients: String = "",
    val unitPrice: Double = 0.0,
    val amount: Int = 0,
    val imageName: String? = null,
    val toppings: List<Topping> = emptyList(),
    val uniqueIdentifier: Long = id
)

fun Product.hasSameContent(other: Product): Boolean {
    return id == other.id &&
            category == other.category &&
            name == other.name &&
            ingredients == other.ingredients &&
            unitPrice == other.unitPrice &&
            imageName == other.imageName &&
            toppings.hasSameContent(other.toppings)
}