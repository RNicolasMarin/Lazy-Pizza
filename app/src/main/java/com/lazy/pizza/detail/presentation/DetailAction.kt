package com.lazy.pizza.detail.presentation

import com.lazy.pizza.core.domain.Product

sealed interface DetailAction {

    data object OnBackPressed: DetailAction

    data class SetProduct(val product: Product): DetailAction

}