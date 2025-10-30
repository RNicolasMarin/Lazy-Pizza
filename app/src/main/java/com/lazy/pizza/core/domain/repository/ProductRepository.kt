package com.lazy.pizza.core.domain.repository

import com.lazy.pizza.core.domain.Product
import com.lazy.pizza.core.domain.ProductsByCategory
import com.lazy.pizza.core.domain.Result

interface ProductRepository {

    suspend fun getProducts(): Result<List<ProductsByCategory>>

    suspend fun getDrinks(): List<Product>

    suspend fun getSauces(): List<Product>
}