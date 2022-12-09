package com.rogergcc.yapechallenge.domain.model


data class Recipe(
    val id: Int,
    val name: String,
    val image: String,
    val description: String?,
    val steps: List<String>,
)
