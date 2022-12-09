package com.rogergcc.yapechallenge.domain.repository

import com.rogergcc.yapechallenge.domain.model.Recipe


interface RecipeNetwork {

    suspend fun getRecipes(): List<Recipe>
}