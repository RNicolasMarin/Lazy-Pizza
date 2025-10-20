package com.lazy.pizza.detail.presentation.di

import com.lazy.pizza.detail.presentation.DetailViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val detailViewModelModule = module {
    viewModelOf(::DetailViewModel)
}