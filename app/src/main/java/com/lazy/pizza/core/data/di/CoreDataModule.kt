package com.lazy.pizza.core.data.di

import com.lazy.pizza.core.data.repository.ProductRepositoryImpl
import com.lazy.pizza.core.domain.ProductRepository
import org.koin.dsl.module

val coreDataModule = module {
    single<ProductRepository> { ProductRepositoryImpl() }
}