package com.lazy.pizza.app

import kotlinx.serialization.Serializable

sealed class Screen {

    @Serializable
    data object Home: Screen()

}