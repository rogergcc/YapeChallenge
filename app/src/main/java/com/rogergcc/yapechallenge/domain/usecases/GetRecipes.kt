package com.rogergcc.yapechallenge.domain.usecases

import com.rogergcc.yapechallenge.domain.model.Recipe
import com.rogergcc.yapechallenge.domain.repository.RecipeNetwork
import com.rogergcc.yapechallenge.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

// TODO: add swipe to refresh

class GetRecipes @Inject constructor(
    private val api: RecipeNetwork,
) {
    fun execute(): Flow<Resource<List<Recipe>>> = flow {
        emit(Resource.Loading(isLoading = true))

        val remoteRecipes = try {
            val response = api.getRecipes()
            response
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        remoteRecipes?.let { recipes ->
            emit(Resource.Success(data = recipes))
        }

        emit(Resource.Loading(isLoading = false))
    }.flowOn(Dispatchers.IO)
}






























