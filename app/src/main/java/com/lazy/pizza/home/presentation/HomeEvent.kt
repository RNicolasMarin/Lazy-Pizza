package com.lazy.pizza.home.presentation

import com.lazy.pizza.core.domain.Product

interface HomeEvent {

    data class ProductAddedToCart(val product: Product): HomeEvent

}