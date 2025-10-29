package com.lazy.pizza.history.presentation.di

import com.lazy.pizza.history.presentation.HistoryViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val historyViewModelModule = module {
    viewModelOf(::HistoryViewModel)
}