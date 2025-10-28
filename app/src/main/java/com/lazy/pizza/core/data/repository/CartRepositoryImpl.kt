package com.lazy.pizza.core.data.repository

import com.lazy.pizza.core.domain.Product
import com.lazy.pizza.core.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartRepositoryImpl: CartRepository {

    /*private val _cartItems = MutableStateFlow<List<Product>>(listOf(Product(
        id = 1,
        category = Category.PIZZA,
        name = "Margherita",
        ingredients = "Tomato sauce, mozzarella, fresh basil, olive oil",
        unitPrice = 8.99,
        amount = 0,
    ),
        Product(
            id = 2,
            category = Category.PIZZA,
            name = "Pepperoni",
            ingredients = "Tomato sauce, mozzarella, pepperoni",
            unitPrice = 9.99,
            amount = 0
        ),
        Product(
            id = 3,
            category = Category.PIZZA,
            name = "Hawaiian",
            ingredients = "Tomato sauce, mozzarella, ham, pineapple",
            unitPrice = 10.49,
            amount = 0
        )))*/
    private val _cartItems = MutableStateFlow<List<Product>>(listOf())

    val cartItems = _cartItems.asStateFlow()

    override fun getCartFlow(): Flow<List<Product>> {
        return cartItems
    }

    override fun addProductToCart(product: Product) {
        val newCart = _cartItems.value.toMutableList()
        newCart.add(product.copy(
            toppings = product.toppings.map {
                it.copy()
            }
        ))
        _cartItems.value = newCart
    }


}