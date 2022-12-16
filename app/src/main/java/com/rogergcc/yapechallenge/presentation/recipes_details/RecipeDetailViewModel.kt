package com.rogergcc.yapechallenge.presentation.recipes_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rogergcc.yapechallenge.domain.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    lateinit var recipe: Recipe

    private val _uiState = MutableStateFlow(RecipeDetailsState())
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<RecipeDetailsEvent>()
    val events = _events.asSharedFlow()


    init {
        savedStateHandle.get<Recipe>("recipeDetail")?.let { recipeDetail ->
            setRecipeDetails(recipeDetail)
        }
    }

    fun onTriggerEvent(event: RecipeDetailsEvent) = viewModelScope.launch {
        when (event) {
            is RecipeDetailsEvent.ShowToast -> {
                _events.emit(RecipeDetailsEvent.ShowToast(message = event.message))
            }
        }
    }

    private fun setRecipeDetails(mRecipe: Recipe) {
//        TimberAppLogger.d(recipes.toString())
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(isLoading = true)
            try {
                mRecipe.let {
                    _uiState.value = uiState.value.copy(isLoading = false)
                    _uiState.value = uiState.value.copy(recipe = mRecipe)
                }
            } catch (ex: Exception) {
                onTriggerEvent(
                    event = RecipeDetailsEvent.ShowToast(
                        message = ex.message ?: "Unknown error"
                    )
                )
            }
        }
    }
}
