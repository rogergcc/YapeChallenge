package com.rogergcc.yapechallenge.presentation.recipes_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rogergcc.yapechallenge.domain.usecases.GetRecipes
import com.rogergcc.yapechallenge.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val getRecipes: GetRecipes,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeListState())
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<RecipeListEvent>()
    val events = _events.asSharedFlow()

    init {
        getRecipes()
    }

    fun onTriggerEvent(event: RecipeListEvent) = viewModelScope.launch {
        when (event) {
            is RecipeListEvent.ShowToast -> {
                _events.emit(RecipeListEvent.ShowToast(message = event.message))
            }
            is RecipeListEvent.NavigateToRecipeDetailsScreen -> {
                _events.emit(RecipeListEvent.NavigateToRecipeDetailsScreen(recipeId = event.recipeId))
            }
        }
    }

    private fun getRecipes() {
        viewModelScope.launch {
            getRecipes.execute()
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { recipes ->
                                _uiState.value = uiState.value.copy(recipes = recipes)
                            }
                        }
                        is Resource.Error -> {
                            onTriggerEvent(
                                event = RecipeListEvent.ShowToast(
                                    message = result.message ?: "Unknown Error!"
                                )
                            )
                        }
                        is Resource.Loading -> {
                            _uiState.value = uiState.value.copy(isLoading = result.isLoading)
                        }
                    }
                }
        }
    }
}





























