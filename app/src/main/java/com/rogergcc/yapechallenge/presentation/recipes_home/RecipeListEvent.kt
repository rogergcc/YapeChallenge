package com.rogergcc.yapechallenge.presentation.recipes_home

import com.rogergcc.yapechallenge.domain.model.Recipe

sealed class RecipeListEvent {

    data class ShowToast(val message: String) : RecipeListEvent()

    data class NavigateToRecipeDetailsScreen(val recipe: Recipe) : RecipeListEvent()

    // TODO: add other events
}