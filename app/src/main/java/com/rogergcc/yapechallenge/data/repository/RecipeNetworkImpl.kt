package com.rogergcc.yapechallenge.data.repository

import com.rogergcc.yapechallenge.data.RecipesApi
import com.rogergcc.yapechallenge.data.remote.mapper.toRecipeD
import com.rogergcc.yapechallenge.domain.model.Recipe
import com.rogergcc.yapechallenge.domain.repository.RecipeNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeNetworkImpl @Inject constructor(
    private val api: RecipesApi,
) : RecipeNetwork {

    override suspend fun getRecipes(): List<Recipe> {
        // TODO: withContext(Dispatchers.IO) { }
        // TODO: provide Dispatchers using Dependency Injection
        return withContext(Dispatchers.IO) {
            api.getRecipes().map {
                it.toRecipeD()
            }
        }
    }
}




















