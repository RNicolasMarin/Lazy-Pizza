package com.lazy.pizza.home.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.lazy.pizza.home.presentation.HomeAction.*

class HomeViewModel(): ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    fun onAction(action: HomeAction) {
        when (action) {
            is UpdateSearchBar -> {
                state = state.copy(
                    searchField = action.searching
                )
            }
        }
    }
}