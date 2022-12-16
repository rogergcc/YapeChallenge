package com.rogergcc.yapechallenge

import android.app.Application
import com.rogergcc.yapechallenge.utils.TimberAppLogger
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class RecipesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        TimberAppLogger.init()
    }
}