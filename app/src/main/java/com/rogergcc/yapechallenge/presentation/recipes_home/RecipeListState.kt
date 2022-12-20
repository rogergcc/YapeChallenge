package com.rogergcc.yapechallenge.presentation.recipes_home

import com.rogergcc.yapechallenge.domain.model.Recipe


data class RecipeListState(
    val recipes: List<Recipe> = emptyList(),
    val isLoading: Boolean = false,
)

























