package com.lazy.pizza.core.data.repository

import com.lazy.pizza.core.domain.Category
import com.lazy.pizza.core.domain.Product
import com.lazy.pizza.core.domain.ProductRepository
import com.lazy.pizza.core.domain.ProductsByCategory
import com.lazy.pizza.core.domain.Result
import kotlinx.coroutines.delay

class ProductRepositoryImpl: ProductRepository {

    companion object {
        val productsByCategory = listOf(
            ProductsByCategory(
                category = Category.PIZZA,
                products = listOf(
                    Product(
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
                    ),
                    Product(
                        id = 4,
                        category = Category.PIZZA,
                        name = "BBQ Chicken",
                        ingredients = "BBQ sauce, mozzarella, grilled chicken, onion, corn",
                        unitPrice = 11.49,
                        amount = 0
                    ),
                    Product(
                        id = 5,
                        category = Category.PIZZA,
                        name = "Four Cheese",
                        ingredients = "Mozzarella, gorgonzola, parmesan, ricotta",
                        unitPrice = 11.99,
                        amount = 0
                    ),
                    Product(
                        id = 6,
                        category = Category.PIZZA,
                        name = "Veggie Delight",
                        ingredients = "Tomato sauce, mozzarella, mushrooms, olives, bell pepper, onion, corn",
                        unitPrice = 9.79,
                        amount = 0
                    ),
                    Product(
                        id = 7,
                        category = Category.PIZZA,
                        name = "Meat Lovers",
                        ingredients = "Tomato sauce, mozzarella, pepperoni, ham, bacon, sausage",
                        unitPrice = 12.49,
                        amount = 0
                    ),
                    Product(
                        id = 8,
                        category = Category.PIZZA,
                        name = "Spicy Inferno",
                        ingredients = "Tomato sauce, mozzarella, spicy salami, jalapeños, red chili pepper, garlic",
                        unitPrice = 11.29,
                        amount = 0
                    ),
                    Product(
                        id = 9,
                        category = Category.PIZZA,
                        name = "Seafood Special",
                        ingredients = "Tomato sauce, mozzarella, shrimp, mussels, squid, parsley",
                        unitPrice = 13.99,
                        amount = 0
                    ),
                    Product(
                        id = 10,
                        category = Category.PIZZA,
                        name = "Truffle Mushroom",
                        ingredients = "Cream sauce, mozzarella, mushrooms, truffle oil, parmesan",
                        unitPrice = 12.99,
                        amount = 0
                    )
                )
            ),
            ProductsByCategory(
                category = Category.DRINKS,
                products = listOf(
                    Product(
                        id = 11,
                        category = Category.DRINKS,
                        name = "Mineral Water",
                        ingredients = "",
                        unitPrice = 1.49,
                        amount = 0,
                        imageName = "mineral water"
                    ),
                    Product(
                        id = 12,
                        category = Category.DRINKS,
                        name = "7-Up",
                        ingredients = "",
                        unitPrice = 1.89,
                        amount = 0,
                        imageName = "7-up"
                    ),
                    Product(
                        id = 13,
                        category = Category.DRINKS,
                        name = "Pepsi",
                        ingredients = "",
                        unitPrice = 1.99,
                        amount = 0,
                        imageName = "pepsi",
                    ),
                    Product(
                        id = 14,
                        category = Category.DRINKS,
                        name = "Orange Juice",
                        ingredients = "",
                        unitPrice = 2.49,
                        amount = 0,
                        imageName = "orange juice",
                    ),
                    Product(
                        id = 15,
                        category = Category.DRINKS,
                        name = "Apple Juice",
                        ingredients = "",
                        unitPrice = 2.29,
                        amount = 0,
                        imageName = "apple juice",
                    ),
                    Product(
                        id = 16,
                        category = Category.DRINKS,
                        name = "Iced Tea (Lemon)",
                        ingredients = "",
                        unitPrice = 2.19,
                        amount = 0,
                        imageName = "iced tea",
                    )
                )
            ),
            ProductsByCategory(
                category = Category.SAUCES,
                products = listOf(
                    Product(
                        id = 17,
                        category = Category.SAUCES,
                        name = "Garlic Sauce",
                        ingredients = "",
                        unitPrice = 0.59,
                        amount = 0
                    ),
                    Product(
                        id = 18,
                        category = Category.SAUCES,
                        name = "BBQ Sauce",
                        ingredients = "",
                        unitPrice = 0.59,
                        amount = 0
                    ),
                    Product(
                        id = 19,
                        category = Category.SAUCES,
                        name = "Cheese Sauce",
                        ingredients = "",
                        unitPrice = 0.89,
                        amount = 0
                    ),
                    Product(
                        id = 20,
                        category = Category.SAUCES,
                        name = "Spicy Chili Sauce",
                        ingredients = "",
                        unitPrice = 0.59,
                        amount = 0
                    )
                )
            ),
            ProductsByCategory(
                category = Category.ICE_CREAM,
                products = listOf(
                    Product(
                        id = 21,
                        category = Category.ICE_CREAM,
                        name = "Vanilla Ice Cream",
                        ingredients = "",
                        unitPrice = 2.49,
                        amount = 0,
                        imageName = "vanilla"
                    ),
                    Product(
                        id = 22,
                        category = Category.ICE_CREAM,
                        name = "Chocolate Ice Cream",
                        ingredients = "",
                        unitPrice = 2.49,
                        amount = 0,
                        imageName = "chocolate"
                    ),
                    Product(
                        id = 23,
                        category = Category.ICE_CREAM,
                        name = "Strawberry Ice Cream",
                        ingredients = "",
                        unitPrice = 2.49,
                        amount = 0,
                        imageName = "strawberry"
                    ),
                    Product(
                        id = 24,
                        category = Category.ICE_CREAM,
                        name = "Cookies Ice Cream",
                        ingredients = "",
                        unitPrice = 2.79,
                        amount = 0,
                        imageName = "cookies"
                    ),
                    Product(
                        id = 25,
                        category = Category.ICE_CREAM,
                        name = "Pistachio Ice Cream",
                        ingredients = "",
                        unitPrice = 2.99,
                        amount = 0,
                        imageName = "pistachio"
                    ),
                    Product(
                        id = 26,
                        category = Category.ICE_CREAM,
                        name = "Mango Sorbet",
                        ingredients = "",
                        unitPrice = 2.69,
                        amount = 0,
                        imageName = "mango sorbet"
                    )
                )
            )
        )
    }

    override suspend fun getProducts(): Result<List<ProductsByCategory>> {
        delay(500)
        return Result.Success(productsByCategory)
    }
}