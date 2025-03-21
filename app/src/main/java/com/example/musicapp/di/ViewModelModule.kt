package com.example.musicapp.di

import androidx.lifecycle.ViewModel
import com.example.musicapp.presentation.viewmodels.DownloadedTracksViewModel
import com.example.musicapp.presentation.viewmodels.HostViewModel
import com.example.musicapp.presentation.viewmodels.SearchTracksViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(DownloadedTracksViewModel::class)
    fun bindDownloadedTracksViewModel(viewModel: DownloadedTracksViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HostViewModel::class)
    fun bindHostsViewModel(viewModel: HostViewModel): ViewModel
}
