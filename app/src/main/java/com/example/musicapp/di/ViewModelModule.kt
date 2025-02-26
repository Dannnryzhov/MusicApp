package com.example.musicapp.di

import androidx.lifecycle.ViewModel
import com.example.musicapp.presentation.viewmodels.TracksListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TracksListViewModel::class)
    fun bindMainTracksViewModel(viewModel: TracksListViewModel): ViewModel
}
