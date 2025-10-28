package com.lazy.pizza.core.domain.repository

import com.lazy.pizza.core.domain.Product
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    fun getCartFlow(): Flow<List<Product>>

    fun addProductToCart(product: Product)

    fun removeProductFromCart(product: Product)

    fun updateProductQuantity(product: Product)

}