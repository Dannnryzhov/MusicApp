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

    private var currentIndex = 0
    private var isLoading = false
    private var hasMore = true

    init {
        fetchTracks()
    }

    fun fetchTracks() {
        if (isLoading || !hasMore) return

        isLoading = true

        viewModelScope.launch {
            try {
                val newTracks = getTracksListUseCase(currentIndex)
                Log.d("MainTracksViewModel", "Fetched ${newTracks.size} tracks")
                if (newTracks.isEmpty()) {
                    hasMore = false
                } else {
                    _tracks.value += newTracks
                    currentIndex += 30
                }
            } catch (e: Exception) {
                Log.e("MainTracksViewModel", "Error fetching tracks", e)
            } finally {
                isLoading = false
            }
        }
    }
}
