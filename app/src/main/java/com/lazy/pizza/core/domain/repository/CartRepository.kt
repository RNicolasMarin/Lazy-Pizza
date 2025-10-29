package com.lazy.pizza.core.domain.repository

import com.lazy.pizza.core.domain.Product
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    fun getCartFlow(): Flow<List<Product>>

    fun getRecommendedFlow(): Flow<List<Product>>

    fun addProductToCart(product: Product)

    fun removeProductFromCart(product: Product)

    fun removeProductFromCart(productPosition: Int)

    fun updateProductQuantity(product: Product)

    fun increaseProductQuantity(productPosition: Int)

    fun decreaseProductQuantity(productPosition: Int)

}