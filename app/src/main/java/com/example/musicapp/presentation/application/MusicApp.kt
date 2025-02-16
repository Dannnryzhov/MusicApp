package com.example.musicapp.presentation.application

import android.app.Application
import com.example.musicapp.di.DaggerAppComponent

class MusicApp : Application() {

    val component by lazy {
        DaggerAppComponent.factory().create(this)
    }
}
