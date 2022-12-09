package com.rogergcc.yapechallenge.data.remote.model

import com.google.gson.annotations.SerializedName

data class RecipeDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("imageURL")
    val imageUrl: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("steps")
    val steps: List<String>,
    @SerializedName("description")

    val description: String,
)
