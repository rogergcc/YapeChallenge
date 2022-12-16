package com.rogergcc.yapechallenge.presentation.recipes_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rogergcc.yapechallenge.domain.model.Recipe
import com.rogergcc.yapechallenge.domain.usecases.GetRecipes
import com.rogergcc.yapechallenge.domain.usecases.SearchRecipes
import com.rogergcc.yapechallenge.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val getRecipes: GetRecipes,
    private val searchRecipes: SearchRecipes,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeListState())
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<RecipeListEvent>()
    val events = _events.asSharedFlow()

    private val query = MutableStateFlow("")
    var queryText: String
        get() = query.value
        set(value) {
            query.value = value
        }

    init {
        getRecipes()
    }

    fun setSearchQuery(search: String) {
        filteredCategories
    }

    val filteredCategories: Flow<List<Recipe>> = query
        .map {
            val criteria = it.lowercase()
            _uiState.value.recipes.filter { category -> criteria in category.name }
        }

    fun applySearchQuery(searchQuery: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        return queries
    }

    fun onTriggerEvent(event: RecipeListEvent) = viewModelScope.launch {
        when (event) {
            is RecipeListEvent.ShowToast -> {
                _events.emit(RecipeListEvent.ShowToast(message = event.message))
            }
            is RecipeListEvent.NavigateToRecipeDetailsScreen -> {
                _events.emit(RecipeListEvent.NavigateToRecipeDetailsScreen(recipe = event.recipe))
            }
        }
    }
    private fun getRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
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

    fun getSearchRecipes(qSearchRecipe: String) {
        val recipes = uiState.value.recipes
//        TimberAppLogger.d(recipes.toString())
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(isLoading = true)
            try {
                if (qSearchRecipe.isEmpty()) {
                    getRecipes()
                    return@launch
                }
                val responseSearch = recipes.filter { recipe ->
                    recipe.name.lowercase().contains(qSearchRecipe.lowercase())
                }
                _uiState.value = uiState.value.copy(isLoading = false)
                _uiState.value = uiState.value.copy(recipes = responseSearch)
            } catch (e: Exception) {
                onTriggerEvent(
                    event = RecipeListEvent.ShowToast(
                        message = e.message ?: "Unknown Error!"
                    )
                )
            }
        }
    }
}





























