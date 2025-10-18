package com.lazy.pizza.core.presentation.designsystem

import com.lazy.pizza.core.domain.Category
import com.lazy.pizza.core.domain.Product

object Urls {

    const val IMAGES_BASE_URL = "https://pl-coding.com/wp-content/uploads/lazypizza/"

    fun getImageUrl(product: Product): String {
        val category = when (product.category) {
            Category.PIZZA -> "pizza"
            Category.DRINKS -> "drink"
            Category.SAUCES -> "sauce"
            Category.ICE_CREAM -> "icecream"
        }
        return "$IMAGES_BASE_URL$category/${product.imageName ?: product.name}.png"
    }
}