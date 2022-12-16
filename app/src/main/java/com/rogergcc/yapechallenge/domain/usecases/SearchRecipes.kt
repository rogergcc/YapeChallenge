package com.rogergcc.yapechallenge.domain.usecases

import com.rogergcc.yapechallenge.domain.model.Recipe
import com.rogergcc.yapechallenge.domain.repository.RecipeNetwork
import com.rogergcc.yapechallenge.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class SearchRecipes @Inject constructor(
    private val api: RecipeNetwork,
) {
    fun getSearch(qRecipeSearch: String): Flow<Resource<List<Recipe>>> = flow {
        emit(Resource.Loading(isLoading = true))
        val remoteRecipes = try {
            val response = api.getRecipes()
            if (qRecipeSearch.isEmpty()) {
                response
            } else {
                val responseSearch = response.filter { recipe ->
                    recipe.name.lowercase().contains(qRecipeSearch.lowercase())
                }
                responseSearch
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        remoteRecipes?.let { recipes ->
            emit(Resource.Loading(isLoading = false))
            emit(Resource.Success(data = recipes))
        }
    }.flowOn(context = Dispatchers.IO)
}