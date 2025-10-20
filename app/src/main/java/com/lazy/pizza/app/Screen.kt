package com.lazy.pizza.app

import com.lazy.pizza.core.domain.Product
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {

    @Serializable
    data object Home: Screen()

    @Serializable
    data class Detail(val product: Product) : Screen()

}