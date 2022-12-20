package com.rogergcc.yapechallenge.utils

import android.widget.ImageView
import coil.load
import coil.size.Scale
import com.rogergcc.yapechallenge.R

fun ImageView.loadImage(imgUrl: String) {
    load(data = imgUrl) {
        scale(Scale.FILL)
        placeholder(R.drawable.recipe_iv_placeholder)
        error(R.drawable.recipe_iv_error)
    }
}

fun ImageView.loadImageFromDrawables(id: Int) {
    load(id) {
        scale(Scale.FILL)
    }
}
