package com.example.musicapp.di

import android.app.Application
import com.example.musicapp.presentation.ui.activities.MainTracksActivity
import com.example.musicapp.presentation.ui.activities.PlayTrackActivity
import com.example.musicapp.presentation.ui.fragments.DownloadedTracksFragment
import com.example.musicapp.presentation.ui.fragments.MainTracksFragment
import com.example.musicapp.presentation.ui.fragments.PlayTrackFragment
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
    fun inject(fragment: DownloadedTracksFragment)
    fun inject(activity: PlayTrackActivity)
    fun inject(fragment: PlayTrackFragment)
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}
