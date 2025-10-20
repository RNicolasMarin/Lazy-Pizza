package com.lazy.pizza.core.domain

import kotlinx.serialization.Serializable

@Serializable
enum class Category {
    PIZZA,
    DRINKS,
    SAUCES,
    ICE_CREAM
}