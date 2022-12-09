package com.rogergcc.yapechallenge.presentation.recipes_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rogergcc.yapechallenge.R


class RecipeDetailsFragment : Fragment(R.layout.fragment_recipe_details) {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_details, container, false)
    }

    companion object
}