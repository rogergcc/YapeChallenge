package com.rogergcc.yapechallenge.presentation.recipes_details

sealed class RecipeDetailsEvent {

    data class ShowToast(val message: String) : RecipeDetailsEvent()

    // TODO: add other events
}