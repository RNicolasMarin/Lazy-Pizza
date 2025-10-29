package com.lazy.pizza.cart.presentation.di

import com.lazy.pizza.cart.presentation.CartViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val cartViewModelModule = module {
    viewModelOf(::CartViewModel)
}