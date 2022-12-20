package com.rogergcc.yapechallenge.data

import com.rogergcc.yapechallenge.data.remote.model.RecipeDto
import retrofit2.http.GET

interface RecipesApi {

    @GET("recipes")
    suspend fun getRecipes(): List<RecipeDto>

    companion object {
        const val BASE_URL = "https://572f47b9-87fa-4c6d-ac33-6bee4a3c8375.mock.pstmn.io"
    }
}
//https://gist.githubusercontent.com/rogergcc/9d0806f34867008569dbb26f1ce7b748/raw/b4ecb58d90b61df13b8af5f902193820e3839dbe/recipes

//https://572f47b9-87fa-4c6d-ac33-6bee4a3c8375.mock.pstmn.io/recipes























