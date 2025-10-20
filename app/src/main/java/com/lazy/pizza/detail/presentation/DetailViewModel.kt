package com.lazy.pizza.detail.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class DetailViewModel: ViewModel() {

    var state by mutableStateOf(DetailState())
        private set

    fun onAction(action: DetailAction) {
        when (action) {
            is DetailAction.SetProduct -> {
                state = state.copy(
                    product = action.product
                )
            }

            DetailAction.OnBackPressed -> Unit
        }
    }
}