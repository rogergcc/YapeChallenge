package com.rogergcc.yapechallenge.di

import com.rogergcc.yapechallenge.data.repository.RecipeNetworkImpl
import com.rogergcc.yapechallenge.domain.repository.RecipeNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindRecipeNetwork(
        recipeNetworkImpl: RecipeNetworkImpl,
    ): RecipeNetwork


}


























