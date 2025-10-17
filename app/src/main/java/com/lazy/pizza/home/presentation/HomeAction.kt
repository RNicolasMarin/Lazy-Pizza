package com.lazy.pizza.home.presentation

sealed interface HomeAction {

    data class UpdateSearchBar(val searching: String) : HomeAction
}