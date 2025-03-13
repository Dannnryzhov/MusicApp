package com.example.musicapp.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.musicapp.domain.models.TrackEntity
import com.example.musicapp.domain.usecases.ManageDownloadedTracksUseCase
import com.example.musicapp.domain.usecases.SearchDownloadedTracksUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class DownloadedTracksViewModel @Inject constructor(
    private val manageDownloadedTracksUseCase: ManageDownloadedTracksUseCase,
    private val searchDownloadedTracksUseCase: SearchDownloadedTracksUseCase
) : SearchTracksViewModel() {

    init {
        viewModelScope.launch {
            manageDownloadedTracksUseCase.getDownloaded().collect { downloadedTracks ->
                mutableTracks.value = downloadedTracks
            }
        }
    }

    override suspend fun performSearch(query: String): List<TrackEntity> {
        return searchDownloadedTracksUseCase(mutableTracks.value, query)
    }

    fun removeFromDownloaded(track: TrackEntity) {
        viewModelScope.launch {
            manageDownloadedTracksUseCase.remove(track)
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
