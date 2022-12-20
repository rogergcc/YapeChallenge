package com.rogergcc.yapechallenge.domain.usecases

import com.google.gson.GsonBuilder
import com.rogergcc.yapechallenge.data.RecipesApi
import com.rogergcc.yapechallenge.data.network.data.RecipeDataValid
import com.rogergcc.yapechallenge.data.network.data.RecipeDataValid.NUM_RECIPES
import com.rogergcc.yapechallenge.data.repository.FakeRecipeNetwork
import com.rogergcc.yapechallenge.utils.Resource
import kotlinx.coroutines.flow.toList

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After

import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection


class GetRecipesTest {
    private lateinit var getRecipes: GetRecipes

    private lateinit var mockWebServer: MockWebServer
    private lateinit var fakeRecipesNetworkFake: FakeRecipeNetwork

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        // TODO: try to change this url
        val mockBaseUrl = mockWebServer.url(path = "/")
        val mockApi = Retrofit.Builder()
            .baseUrl(mockBaseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(RecipesApi::class.java)

        fakeRecipesNetworkFake = FakeRecipeNetwork(api = mockApi)

        getRecipes = GetRecipes(
            api = fakeRecipesNetworkFake,
        )
    }

    @Test
    fun `Get Recipes UseCase`(): Unit = runBlocking {
        // mockWebServer setup and schedule some response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(body = RecipeDataValid.data)
        )
        // execute the use-case
        val emissions = getRecipes.execute().toList()

        assert((emissions[0] as Resource.Loading).isLoading)

        assert((emissions[0] as Resource.Loading).data.isNullOrEmpty())

        assert((emissions[1] as Resource.Success).data?.size == NUM_RECIPES)
//
        assert((emissions[2] as Resource.Loading).isLoading.not())
    }


    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

}