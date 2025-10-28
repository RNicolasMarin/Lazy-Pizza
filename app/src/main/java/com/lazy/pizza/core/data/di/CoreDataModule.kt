package com.lazy.pizza.core.data.di

import com.lazy.pizza.core.data.repository.CartRepositoryImpl
import com.lazy.pizza.core.data.repository.ProductRepositoryImpl
import com.lazy.pizza.core.data.repository.ToppingRepositoryImpl
import com.lazy.pizza.core.domain.repository.CartRepository
import com.lazy.pizza.core.domain.repository.ProductRepository
import com.lazy.pizza.core.domain.repository.ToppingRepository
import org.koin.dsl.module

val coreDataModule = module {
    single<ProductRepository> { ProductRepositoryImpl() }
    single<ToppingRepository> { ToppingRepositoryImpl() }
    single<CartRepository> { CartRepositoryImpl() }
}