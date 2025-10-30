package com.lazy.pizza.core.data.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lazy.pizza.core.domain.Category
import com.lazy.pizza.core.domain.Product
import com.lazy.pizza.core.domain.ProductsByCategory
import com.lazy.pizza.core.domain.Result
import com.lazy.pizza.core.domain.repository.ProductRepository
import kotlinx.coroutines.tasks.await

class ProductRepositoryImplFirebase: ProductRepository {

    companion object {

        var pizzas: List<Product> = emptyList()
        var drinks: List<Product> = emptyList()
        var sauces: List<Product> = emptyList()
        var iceCream: List<Product> = emptyList()
        var productsByCategory: List<ProductsByCategory> = emptyList()

    }

    override suspend fun getProducts(): Result<List<ProductsByCategory>> {

        val db = Firebase.firestore

        if (productsByCategory.isEmpty()) {
            val snapshot = db.collection("/productsByCategory").get().await()
            val productsByCategoryResult = snapshot.documents.mapNotNull { it.toObject(ProductsByCategory::class.java) }
            productsByCategory = productsByCategoryResult.sortedBy {
                Category.entries.indexOf(it.category)
            }
            productsByCategory.forEach {
                when (it.category) {
                    Category.PIZZA -> pizzas = it.products
                    Category.DRINKS -> drinks = it.products
                    Category.SAUCES -> sauces = it.products
                    Category.ICE_CREAM -> iceCream = it.products
                }
            }
        }

        /*
        // Upload the entire category as a single document
        db.collection("productsByCategory")
            .document("IceCream") // document name
            .set(pizzaCategory)
            .addOnSuccessListener {
                Log.d("Firestore", "Uploaded Pizza category successfully!")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error uploading data", e)
            }
        *
        * */

        return Result.Success(
            productsByCategory
        )
    }

    override suspend fun getDrinks(): List<Product> {
        return drinks
    }

    override suspend fun getSauces(): List<Product> {
        return sauces
    }

}