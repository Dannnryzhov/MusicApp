package com.example.musicapp.di

import androidx.lifecycle.ViewModel
import com.example.musicapp.presentation.viewmodels.DownloadedTracksViewModel
import com.example.musicapp.presentation.viewmodels.MainTracksViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainTracksViewModel::class)
    fun bindMainTracksViewModel(viewModel: MainTracksViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DownloadedTracksViewModel::class)
    abstract fun bindMovieDetailViewModel(downloadedTracksViewModel: DownloadedTracksViewModel): ViewModel
}
