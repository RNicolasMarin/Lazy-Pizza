package com.lazy.pizza.core.data.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lazy.pizza.core.domain.Result
import com.lazy.pizza.core.domain.Topping
import com.lazy.pizza.core.domain.repository.ToppingRepository
import kotlinx.coroutines.tasks.await

class ToppingRepositoryImplFirebase: ToppingRepository {

    companion object {
        var toppings: List<Topping> = emptyList()
    }

    override suspend fun getToppings(): Result<List<Topping>> {
        val db = Firebase.firestore
        /*ToppingRepositoryImplLocal.toppings.forEach {
            db.collection("toppings")
                .document(it.name)
                .set(it)
                .addOnSuccessListener {
                    Log.d("Firestore", "Uploaded Pizza category successfully!")
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Error uploading data", e)
                }
        }*/

        if (toppings.isEmpty()) {
            val snapshot = db.collection("/toppings").get().await()
            val result = snapshot.documents.mapNotNull { it.toObject(Topping::class.java) }
            toppings = result.sortedBy { it.id }
        }

        return Result.Success(toppings)
    }
}