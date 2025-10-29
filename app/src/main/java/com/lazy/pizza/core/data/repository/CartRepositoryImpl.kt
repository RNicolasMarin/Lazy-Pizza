package com.lazy.pizza.core.data.repository

import com.lazy.pizza.core.domain.Product
import com.lazy.pizza.core.domain.hasSameContent
import com.lazy.pizza.core.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

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

    override fun getRecommendedFlow(): Flow<List<Product>> {
        return _cartItems
            .map { inCart ->
                val sauces = ProductRepositoryImpl.sauces.filter { product ->
                    inCart.none { product.id == it.id }
                }
                val drinks = ProductRepositoryImpl.drinks.filter { product ->
                    inCart.none { product.id == it.id }
                }
                sauces + drinks
            }
            .distinctUntilChanged { old, new ->
                old.size == new.size && old.map { it.id }.toSet() == new.map { it.id }.toSet()
            }
            // Shuffle *only when* the content changes
            .map { it.shuffled() }
    }

    var uniqueIdentifier = 0L

    override fun addProductToCart(product: Product) {
        var isNewItem = true
        val newCart = _cartItems.value.map {
            if (it.hasSameContent(product)) {
                isNewItem = false
                it.copy(amount = it.amount + 1)
            } else {
                it
            }
        }.toMutableList()

        if (isNewItem) {
            newCart.add(
                product.copy(
                    uniqueIdentifier = uniqueIdentifier,
                    toppings = product.toppings.map {
                        it.copy()
                    }
                )
            )
            uniqueIdentifier++
        }

        _cartItems.value = newCart
    }

    override fun removeProductFromCart(product: Product) {
        _cartItems.value = _cartItems.value.filter {
            it.id != product.id || it.amount != product.amount
        }
    }

    override fun removeProductFromCart(productPosition: Int) {
        _cartItems.value = _cartItems.value.filterIndexed { index, it ->
            index != productPosition
        }
    }

    override fun updateProductQuantity(product: Product) {
        _cartItems.value = _cartItems.value.map {
            if (it.id == product.id) {
                it.copy(amount = product.amount)
            } else {
                it
            }
        }
    }

    override fun increaseProductQuantity(productPosition: Int) {
        _cartItems.value = _cartItems.value.mapIndexed { index, it ->
            if (index == productPosition) {
                it.copy(amount = it.amount + 1)
            } else {
                it
            }
        }
    }

    override fun decreaseProductQuantity(productPosition: Int) {
        _cartItems.value = _cartItems.value.mapIndexed { index, it ->
            if (index == productPosition) {
                it.copy(amount = it.amount - 1)
            } else {
                it
            }
        }
    }

}