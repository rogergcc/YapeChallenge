package com.rogergcc.yapechallenge.data.remote.mapper

import com.rogergcc.yapechallenge.data.remote.model.RecipeDto
import com.rogergcc.yapechallenge.domain.model.Recipe


fun RecipeDto.toRecipeD(): Recipe {
    return Recipe(
        id = id,
        name = name,
        image = imageUrl,
        steps = steps,
        description = description
    )
}