package com.lazy.pizza.core.domain

interface ProductRepository {

    suspend fun getProducts(): Result<List<ProductsByCategory>>
}