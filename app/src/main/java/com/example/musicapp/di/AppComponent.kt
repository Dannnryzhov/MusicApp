package com.example.musicapp.di

import android.app.Application
import com.example.musicapp.presentation.ui.activities.MainTracksActivity
import com.example.musicapp.presentation.ui.fragments.MainTracksFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        ViewModelModule::class,
        DatabaseModule::class
    ]
)
interface AppComponent {
    fun inject(activity: MainTracksActivity)
    fun inject(fragment: MainTracksFragment)
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}
