package com.rogergcc.yapechallenge.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val id: Int,
    val name: String,
    val image: String,
    val description: String?,
    val steps: List<String>,
) : Parcelable
