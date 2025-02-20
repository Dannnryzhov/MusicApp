package com.example.musicapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.domain.models.TrackEntity
import com.example.musicapp.domain.usecases.ManageDownloadedTracksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DownloadedTracksViewModel @Inject constructor(
    private val manageDownloadedTracksUseCase: ManageDownloadedTracksUseCase
) : ViewModel() {

    private val _tracks = MutableStateFlow<List<TrackEntity>>(emptyList())
    val tracks: StateFlow<List<TrackEntity>> = _tracks

    init {
        viewModelScope.launch {
            manageDownloadedTracksUseCase.getDownloaded().collect { downloadedTracks ->
                _tracks.value = downloadedTracks
            }
        }
    }

    fun removeFromDownloaded(track: TrackEntity) {
        viewModelScope.launch {
            manageDownloadedTracksUseCase.remove(track)
        }
    }
}
