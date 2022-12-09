package com.rogergcc.yapechallenge.presentation.recipes_home

sealed class RecipeListEvent {

    data class ShowToast(val message: String) : RecipeListEvent()

    data class NavigateToRecipeDetailsScreen(val recipeId: Int) : RecipeListEvent()

    // TODO: add other events
}