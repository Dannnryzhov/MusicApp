package com.example.musicapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.domain.models.TrackEntity
import com.example.musicapp.domain.usecases.ManageDownloadedTracksUseCase
import com.example.musicapp.domain.usecases.SearchDownloadedTracksUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DownloadedTracksViewModel @Inject constructor(
    private val manageDownloadedTracksUseCase: ManageDownloadedTracksUseCase,
    private val searchDownloadedTracksUseCase: SearchDownloadedTracksUseCase
) : ViewModel() {

    private val _tracks = MutableStateFlow<List<TrackEntity>>(emptyList())
    val tracks: StateFlow<List<TrackEntity>> = _tracks.asStateFlow()

    private val _searchResults = MutableStateFlow<List<TrackEntity>>(emptyList())
    val searchResults: StateFlow<List<TrackEntity>> = _searchResults.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("DownloadedTracksVM", "Error collecting downloaded tracks", exception)
    }

    init {
        viewModelScope.launch(exceptionHandler) {
            manageDownloadedTracksUseCase.getDownloaded().collect { downloadedTracks ->
                _tracks.value = downloadedTracks
            }
        }
    }

    fun search(query: String) {
        if (query.isBlank()) {
            _searchResults.value = _tracks.value
            return
        }
        viewModelScope.launch(exceptionHandler) {
            val results = searchDownloadedTracksUseCase(_tracks.value, query)
            _searchResults.value = results
        }
    }

    fun removeFromDownloaded(track: TrackEntity) {
        viewModelScope.launch(exceptionHandler) {
            manageDownloadedTracksUseCase.remove(track)
        }
    }
}
