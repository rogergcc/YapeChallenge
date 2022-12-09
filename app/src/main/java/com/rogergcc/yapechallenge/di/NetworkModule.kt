package com.rogergcc.yapechallenge.di

import com.google.gson.GsonBuilder
import com.rogergcc.yapechallenge.data.RecipesApi
import com.rogergcc.yapechallenge.data.RecipesApi.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRecipesApi(): RecipesApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(RecipesApi::class.java)
    }
}



























