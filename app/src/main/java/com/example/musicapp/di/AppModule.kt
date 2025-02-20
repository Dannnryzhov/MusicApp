package com.example.musicapp.di

import com.example.musicapp.data.repository.TracksRepositoryImpl
import com.example.musicapp.domain.repository.TracksRepository
import dagger.Binds
import dagger.Module

@Module
interface AppModule {
    @Binds
    fun bindTracksRepository(impl: TracksRepositoryImpl): TracksRepository
}
