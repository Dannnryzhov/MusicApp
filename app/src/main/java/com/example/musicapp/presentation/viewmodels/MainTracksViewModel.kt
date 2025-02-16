package com.example.musicapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.domain.models.TrackEntity
import com.example.musicapp.domain.usecases.GetTracksListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainTracksViewModel @Inject constructor(
    private val getTracksListUseCase: GetTracksListUseCase
) : ViewModel() {

    private val _tracks = MutableStateFlow<List<TrackEntity>>(emptyList())
    val tracks: StateFlow<List<TrackEntity>> = _tracks

    init {
        fetchTracks()
    }

    fun fetchTracks() {
        viewModelScope.launch {
            try {
                val tracksList = getTracksListUseCase(0)
                Log.d("MainTracksViewModel", "Fetched ${tracksList.size} tracks")
                _tracks.value = tracksList
            } catch (e: Exception) {
                Log.e("MainTracksViewModel", "Error fetching tracks", e)
            }
        }
    }
}
