package com.rogergcc.yapechallenge.data.repository

import com.rogergcc.yapechallenge.data.RecipesApi
import com.rogergcc.yapechallenge.data.remote.mapper.toRecipeD
import com.rogergcc.yapechallenge.domain.model.Recipe
import com.rogergcc.yapechallenge.domain.repository.RecipeNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created on diciembre.
 * year 2022 .
 */

class FakeRecipeNetwork(
    private val api: RecipesApi,
) : RecipeNetwork {

    override suspend fun getRecipes(): List<Recipe> {
        return withContext(Dispatchers.IO) {
            api.getRecipes().map {
                it.toRecipeD()
            }
        }
    }
}