package com.lazy.pizza.home.presentation.di

import com.lazy.pizza.home.presentation.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homeViewModelModule = module {
    viewModelOf(::HomeViewModel)
}