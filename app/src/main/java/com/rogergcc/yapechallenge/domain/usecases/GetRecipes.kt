package com.rogergcc.yapechallenge.domain.usecases

import com.rogergcc.yapechallenge.domain.model.Recipe
import com.rogergcc.yapechallenge.domain.repository.RecipeNetwork
import com.rogergcc.yapechallenge.utils.Resource
import com.rogergcc.yapechallenge.utils.TimberAppLogger
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
            TimberAppLogger.d("api.getRecipes")
            val response = api.getRecipes()
            response
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        remoteRecipes?.let { recipes ->
            emit(Resource.Loading(isLoading = false))
            emit(Resource.Success(data = recipes))
        }

    }.flowOn(Dispatchers.IO)
}






























