package com.lazy.pizza.detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazy.pizza.core.domain.Product
import com.lazy.pizza.core.domain.Result
import com.lazy.pizza.core.domain.Topping
import com.lazy.pizza.core.domain.repository.CartRepository
import com.lazy.pizza.core.domain.repository.ToppingRepository
import com.lazy.pizza.detail.presentation.DetailAction.*
import com.lazy.pizza.detail.presentation.DetailAction.ActionAffectingToppingQuantity.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: ToppingRepository,
    private val cartRepository: CartRepository,
): ViewModel() {

    private val product = MutableStateFlow<Product?>(null)
    private val toppings = MutableStateFlow<List<Topping>>(emptyList())

    var state: StateFlow<DetailState> = combine(
        product,
        toppings
    ) { product, toppings ->
        DetailState(
            product = product,
            toppings = toppings,
            cardTotal = (product?.unitPrice ?: 0.0) + toppings.sumOf { it.amount * it.unitPrice }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DetailState()
    )

    private val eventChannel = Channel<DetailEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            val toppingsResult = repository.getToppings()

            if (toppingsResult is Result.Success) {
                toppings.value = toppingsResult.data
            }
        }
    }
    fun onAction(action: DetailAction) {
        when (action) {
            is SetProduct -> {
                product.value = action.product
            }

            is ActionAffectingToppingQuantity -> {
                toppings.value = toppings.value.map { topping ->
                    if (topping.id == action.topping.id) {
                        when (action) {
                            is AddToCart -> {
                                topping.copy(
                                    amount = 1
                                )
                            }
                            is IncreaseFromCart -> {
                                topping.copy(
                                    amount = topping.amount + 1,
                                )
                            }
                            is ReduceFromCart -> {
                                topping.copy(
                                    amount = topping.amount - 1,
                                )
                            }
                        }
                    } else {
                        topping
                    }
                }
            }

            AddProductAndToppingsToCart -> {
                viewModelScope.launch {
                    product.value?.let {

                        cartRepository.addProductToCart(
                            it.copy(
                                toppings = toppings.value.filter { topping ->
                                    topping.amount > 0
                                }
                            )
                        )

                        eventChannel.send(DetailEvent.GoBackToHome)
                    }
                }
            }

            OnBackPressed -> Unit
        }
    }
}