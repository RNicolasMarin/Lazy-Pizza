package com.lazy.pizza.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazy.pizza.core.domain.repository.ProductRepository
import com.lazy.pizza.core.domain.ProductsByCategory
import com.lazy.pizza.core.domain.Result
import com.lazy.pizza.core.domain.repository.CartRepository
import com.lazy.pizza.home.presentation.HomeAction.*
import com.lazy.pizza.home.presentation.HomeAction.ActionAffectingProductQuantity.*
import com.lazy.pizza.home.presentation.HomeEvent.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ProductRepository,
    private val cartRepository: CartRepository,
): ViewModel() {

    private val allProductsByCategory = MutableStateFlow<List<ProductsByCategory>>(emptyList())
    private val searchField = MutableStateFlow("")

    var state: StateFlow<HomeState> = combine(
        allProductsByCategory,
        searchField,
        cartRepository.getCartFlow()
    ) { allProducts, field, cart ->
        val filtered = allProducts.mapNotNull {
            val filteredProducts = it.products.filter { product ->
                product.name.contains(field.trim(), ignoreCase = true)
            }
            if (filteredProducts.isNotEmpty()) {
                it.copy(products = filteredProducts)
            } else {
                null // exclude empty categories
            }
        }
        HomeState(
            searchField = field,
            productsByCategories = filtered,
            cartAmount = cart.sumOf { it.amount }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeState()
    )

    private val eventChannel = Channel<HomeEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            val products = repository.getProducts()

            if (products is Result.Success) {
                allProductsByCategory.value = products.data
            }
        }
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is UpdateSearchBar -> {
                searchField.value = action.searching
            }

            is ActionAffectingProductQuantity -> {
                allProductsByCategory.value = allProductsByCategory.value.map {
                    if (it.category == action.product.category) {
                        it.copy(
                            products = it.products.map { product ->
                                if (product.id == action.product.id) {
                                    when (action) {
                                        is AddToCart -> {
                                            val modifiedProduct = product.copy(amount = 1)
                                            viewModelScope.launch {
                                                cartRepository.addProductToCart(
                                                    modifiedProduct
                                                )
                                                eventChannel.send(
                                                    ProductAddedToCart(
                                                        modifiedProduct
                                                    )
                                                )
                                            }
                                            modifiedProduct
                                        }
                                        is DeleteFromCart -> {
                                            cartRepository.removeProductFromCart(product)
                                            product.copy(amount = 0)
                                        }
                                        is IncreaseFromCart -> {
                                            val modifiedProduct = product.copy(amount = product.amount + 1)
                                            cartRepository.updateProductQuantity(modifiedProduct)
                                            modifiedProduct
                                        }
                                        is ReduceFromCart -> {
                                            val modifiedProduct = product.copy(amount = product.amount - 1)
                                            if (modifiedProduct.amount == 0) {
                                                cartRepository.removeProductFromCart(product)
                                            } else {
                                                cartRepository.updateProductQuantity(modifiedProduct)
                                            }
                                            modifiedProduct
                                        }
                                    }
                                } else {
                                    product
                                }
                            }
                        )
                    } else {
                        it
                    }
                }
            }

            is ProductSelected -> Unit
        }
    }
}