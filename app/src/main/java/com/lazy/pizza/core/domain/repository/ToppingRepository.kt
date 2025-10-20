package com.lazy.pizza.core.domain.repository

import com.lazy.pizza.core.domain.Result
import com.lazy.pizza.core.domain.Topping

interface ToppingRepository {

    suspend fun getToppings(): Result<List<Topping>>

}