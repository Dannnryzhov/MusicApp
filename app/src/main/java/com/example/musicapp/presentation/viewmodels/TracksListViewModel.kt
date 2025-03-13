package com.example.musicapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.musicapp.domain.models.TrackEntity
import com.example.musicapp.domain.usecases.GetTracksListUseCase
import com.example.musicapp.domain.usecases.ManageDownloadedTracksUseCase
import com.example.musicapp.domain.usecases.SearchTracksUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class TracksListViewModel @Inject constructor(
    private val getTracksListUseCase: GetTracksListUseCase,
    private val manageDownloadedTracksUseCase: ManageDownloadedTracksUseCase,
    private val searchTracksUseCase: SearchTracksUseCase
) : SearchTracksViewModel() {

    init {
        fetchTracks()
    }

    private fun fetchTracks() {
        viewModelScope.launch(exceptionHandler) {
            val tracksList = getTracksListUseCase(0)
            Log.d("TracksListVM", "Fetched ${tracksList.size} tracks")
            mutableTracks.value = tracksList
        }
    }

    override suspend fun performSearch(query: String): List<TrackEntity> {
        return searchTracksUseCase(query)
    }

    fun toggleDownloadedTrack(track: TrackEntity) {
        viewModelScope.launch(exceptionHandler) {
                manageDownloadedTracksUseCase.add(track)
        }
    }

    fun triggerTestEvent() {
        viewModelScope.launch {
            sendEvent(TracksListEvents.ShowTrackListDialog("Тестовый ивент для проверки диалога"))
        }
    }

    fun triggerTestError() {
        viewModelScope.launch(exceptionHandler) {
            throw RuntimeException("Тестовая ошибка для проверки диалога")
        }
    }
}
