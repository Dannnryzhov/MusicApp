package com.example.musicapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.domain.models.TrackEntity
import com.example.musicapp.domain.usecases.GetTracksListUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TracksListViewModel @Inject constructor(
    private val getTracksListUseCase: GetTracksListUseCase
) : ViewModel() {

    private val _tracks = MutableStateFlow<List<TrackEntity>>(emptyList())
    val tracks: StateFlow<List<TrackEntity>> = _tracks.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("MainTracksViewModel", "Error fetching tracks", exception)
    }

    init {
        fetchTracks()
    }

    fun fetchTracks() {
        viewModelScope.launch {
            viewModelScope.launch(exceptionHandler) {
                val tracksList = getTracksListUseCase(0)
                Log.d("MainTracksViewModel", "Fetched ${tracksList.size} tracks")
                _tracks.value = tracksList
            }
        }
    }
}
