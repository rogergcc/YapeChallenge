package com.rogergcc.yapechallenge.presentation.recipes_home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        collectUIState()
        collectUIEvents()
    }

    private fun initRecyclerView() {
        binding.rvRecipesList.apply {
            adapter = recipeListAdapter
            setHasFixedSize(true)
        }

        recipeListAdapter.setOnItemClickListener { recipeId ->
            viewModel.onTriggerEvent(RecipeListEvent.NavigateToRecipeDetailsScreen(recipeId = recipeId))
        }
    }

    private fun navigateToRecipeDetailsScreen(recipeId: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToRecipeDetailsFragment(
            recipeId = recipeId
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

        }
    }

    private fun collectUIEvents() = lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

            viewModel.events.collect { event ->
                when (event) {
                    is RecipeListEvent.ShowToast -> {
                        Snackbar.make(
                            binding.root,
                            event.message,
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                    is RecipeListEvent.NavigateToRecipeDetailsScreen -> {
                        navigateToRecipeDetailsScreen(recipeId = event.recipeId)
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