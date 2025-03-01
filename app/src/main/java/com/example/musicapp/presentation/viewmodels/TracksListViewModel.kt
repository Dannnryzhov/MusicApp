package com.example.musicapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.domain.models.TrackEntity
import com.example.musicapp.domain.usecases.GetTracksListUseCase
import com.example.musicapp.domain.usecases.ManageDownloadedTracksUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TracksListViewModel @Inject constructor(
    private val getTracksListUseCase: GetTracksListUseCase,
    private val manageDownloadedTracksUseCase: ManageDownloadedTracksUseCase
) : ViewModel() {

    private val _tracks = MutableStateFlow<List<TrackEntity>>(emptyList())
    val tracks: StateFlow<List<TrackEntity>> = _tracks.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("TracksListVM", "Error fetching tracks", exception)
    }

    init {
        fetchTracks()
    }

    fun fetchTracks() {
        viewModelScope.launch(exceptionHandler) {
            val tracksList = getTracksListUseCase(0)
            Log.d("TracksListVM", "Fetched ${tracksList.size} tracks")
            _tracks.value = tracksList }
    }

    fun toggleDownloadedTrack(track: TrackEntity) {
        viewModelScope.launch {
                manageDownloadedTracksUseCase.add(track)
        }
    }
}
