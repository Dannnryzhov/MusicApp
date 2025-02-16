package com.example.musicapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.domain.models.TrackEntity
import com.example.musicapp.domain.usecases.SearchTracksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchTracksViewModel @Inject constructor(
    private val searchTracksUseCase: SearchTracksUseCase
) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<TrackEntity>>(emptyList())
    val searchResults: StateFlow<List<TrackEntity>> = _searchResults

    fun search(query: String) {
        viewModelScope.launch {
            try {
                val results = searchTracksUseCase(query)
                _searchResults.value = results
            } catch (e: Exception) {
                Log.e("SearchTracksViewModel", "Error searching tracks", e)
            }
        }
    }
}
