package com.rogergcc.yapechallenge.presentation.recipes_home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.rogergcc.yapechallenge.R
import com.rogergcc.yapechallenge.databinding.FragmentHomeBinding
import com.rogergcc.yapechallenge.domain.model.Recipe
import com.rogergcc.yapechallenge.utils.TimberAppLogger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val recipeListAdapter = RecipeListAdapter()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<RecipeListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        binding.edtSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                //For this example only use seach option
                //U can use a other view with activityresult

                performSearch()
                viewModel.getSearchRecipes(binding.edtSearch.text.toString())
                return@OnEditorActionListener true
            }
            false
        })
        collectUIState()
        collectUIEvents()
    }

    private fun performSearch() {
        binding.edtSearch.clearFocus()
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.edtSearch.windowToken, 0)
    }


    //    private fun initListenersSearch() {
//        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
//            androidx.appcompat.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                newText?.let { viewModel.setSearchQuery(it) }
//                return true
//            }
//        }
//        )
//        search.requestFocus()
//    }
    private fun initRecyclerView() {
        binding.rvRecipesList.apply {
            adapter = recipeListAdapter
            setHasFixedSize(true)
        }

        recipeListAdapter.setOnItemClickListener { recipe ->
            TimberAppLogger.d("recipe setOnItemClickListener => $recipe")
            viewModel.onTriggerEvent(RecipeListEvent.NavigateToRecipeDetailsScreen(recipe))
        }
    }

    private fun navigateToRecipeDetailsScreen(
        recipe: Recipe,
    ) {
        val action = HomeFragmentDirections.actionHomeFragmentToRecipeDetailsFragment(
            recipeId = 0, recipeDetail = recipe
        )
        findNavController().navigate(action)
    }

    private fun collectUIState() = lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

            viewModel.uiState.collect { state ->
                binding.apply {
                    pbLoading.isVisible = state.isLoading
                    if (!state.isLoading) {
                        recipeListAdapter.submitList(state.recipes)
                    }
                }
            }
//            viewModel.filteredCategories.collect{ recipeListFound->
//                binding.apply {
//
//                        recipeListAdapter.submitList(recipeListFound)
//
//                }
//            }
        }
    }

    private fun collectUIEvents() = lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

            viewModel.events.collect { recipeListEvent ->
                when (recipeListEvent) {
                    is RecipeListEvent.ShowToast -> {
                        Snackbar.make(
                            binding.root,
                            recipeListEvent.message,
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                    is RecipeListEvent.NavigateToRecipeDetailsScreen -> {
                        navigateToRecipeDetailsScreen(recipeListEvent.recipe)
                    }

                }
            }


        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}