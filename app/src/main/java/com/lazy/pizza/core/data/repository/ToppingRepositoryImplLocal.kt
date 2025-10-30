package com.lazy.pizza.core.data.repository

import com.lazy.pizza.core.domain.Result
import com.lazy.pizza.core.domain.Topping
import com.lazy.pizza.core.domain.repository.ToppingRepository
import kotlinx.coroutines.delay

class ToppingRepositoryImplLocal: ToppingRepository {

    companion object {
        val toppings = listOf(
            Topping(id = 1, name = "Bacon", unitPrice = 1.00, amount = 0, imageName = "bacon"),
            Topping(id = 2, name = "Extra Cheese", unitPrice = 1.00, amount = 0, imageName = "cheese"),
            Topping(id = 3, name = "Corn", unitPrice = 0.50, amount = 0, imageName = "corn"),
            Topping(id = 4, name = "Tomato", unitPrice = 0.50, amount = 0, imageName = "tomato"),
            Topping(id = 5, name = "Olives", unitPrice = 0.50, amount = 0, imageName = "olive"),
            Topping(id = 6, name = "Pepperoni", unitPrice = 1.00, amount = 0, imageName = "pepperoni"),
            Topping(id = 7, name = "Mushrooms", unitPrice = 0.50, amount = 0, imageName = "mashroom"),
            Topping(id = 8, name = "Basil", unitPrice = 0.50, amount = 0, imageName = "basil"),
            Topping(id = 9, name = "Pineapple", unitPrice = 1.00, amount = 0, imageName = "pineapple"),
            Topping(id = 10, name = "Onion", unitPrice = 0.50, amount = 0, imageName = "onion"),
            Topping(id = 11, name = "Chili Peppers", unitPrice = 0.50, amount = 0, imageName = "chilli"),
            Topping(id = 12, name = "Spinach", unitPrice = 0.50, amount = 0, imageName = "spinach")
        )
    }

    override suspend fun getToppings(): Result<List<Topping>> {
        delay(500)
        return Result.Success(toppings)
    }
}