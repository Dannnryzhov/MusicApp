package com.example.musicapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.domain.models.TrackEntity
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    private val exceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("BaseTracksVM", "Ошибка при выполнении запроса", exception)
    }
) : ViewModel() {

    protected val mutableTracks = MutableStateFlow<List<TrackEntity>>(emptyList())
    val tracks: StateFlow<List<TrackEntity>> = mutableTracks.asStateFlow()

    private val mutableSearchResults = MutableStateFlow<List<TrackEntity>>(emptyList())
    val searchResults: StateFlow<List<TrackEntity>> = mutableSearchResults.asStateFlow()

    protected abstract suspend fun performSearch(query: String): List<TrackEntity>

    fun search(query: String) {
        if (query.isBlank()) {
            mutableSearchResults.value = mutableTracks.value
            return
        }
        viewModelScope.launch(exceptionHandler) {
            val results = performSearch(query)
            mutableSearchResults.value = results
        }
    }
}

