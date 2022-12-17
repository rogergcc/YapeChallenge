package com.rogergcc.yapechallenge.presentation.recipes_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.rogergcc.yapechallenge.R
import com.rogergcc.yapechallenge.databinding.FragmentRecipeDetailsBinding
import com.rogergcc.yapechallenge.utils.loadImage


class RecipeDetailsFragment : Fragment(R.layout.fragment_recipe_details) {

    private var _binding: FragmentRecipeDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<RecipeDetailViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRecipeDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectUIState()
        collectUIEvents()
        binding.ivUbicationMap.setOnClickListener {
            navigateToRecipeDetailsScreen()
        }
    }

    private fun navigateToRecipeDetailsScreen(
    ) {
        val action = RecipeDetailsFragmentDirections.actionRecipeDetailsFragmentToMapsFragment(
        )
        findNavController().navigate(action)
    }

    private fun collectUIState() = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        viewModel.uiState.collect { state ->
            binding.apply {
                pbLoading.isVisible = state.isLoading
                if (!state.isLoading) {
                    state.recipe?.let { recipe ->
                        ivRecipeImage.loadImage(imgUrl = recipe.image)
                        tvRecipeName.text = recipe.name

                    }
                }
            }
        }
    }

    private fun collectUIEvents() = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        viewModel.events.collect { event ->
            when (event) {
                is RecipeDetailsEvent.ShowToast -> {
                    Snackbar.make(
                        binding.root,
                        event.message,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}