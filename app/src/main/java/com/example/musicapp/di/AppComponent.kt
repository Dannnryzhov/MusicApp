package com.example.musicapp.di

import android.app.Application
import com.example.musicapp.presentation.ui.activities.TracksListActivity
import com.example.musicapp.presentation.ui.fragments.DownloadedTracksFragment
import com.example.musicapp.presentation.ui.fragments.TracksListFragment
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
    fun inject(activity: TracksListActivity)
    fun inject(fragment: TracksListFragment)
    fun inject(fragment: DownloadedTracksFragment)
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}
