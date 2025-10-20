package com.lazy.pizza.core.presentation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.lazy.pizza.core.domain.Product
import kotlinx.serialization.json.Json

object ProductNavType : NavType<Product>(isNullableAllowed = false) {
    override fun put(bundle: Bundle, key: String, value: Product) {
        bundle.putString(key, Json.encodeToString(value))
    }

    override fun get(bundle: Bundle, key: String): Product {
        return Json.decodeFromString(bundle.getString(key)!!)
    }

    override fun parseValue(value: String): Product {
        return Json.decodeFromString(Uri.decode(value))
    }

    override fun serializeAsValue(value: Product): String {
        return Uri.encode(Json.encodeToString(value))
    }
}