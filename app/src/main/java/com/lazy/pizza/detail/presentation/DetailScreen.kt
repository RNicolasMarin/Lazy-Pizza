package com.lazy.pizza.detail.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.lazy.pizza.core.domain.Product

@Composable
fun DetailScreenRoot(
    product: Product
) {
    Text(text = product.toString())
}