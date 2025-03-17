package com.example.musicapp.presentation.viewmodels

sealed interface TracksListEvents : Event {
    data class ShowTrackListDialog(val message: String): TracksListEvents
    data class ShowDownloadedListDialog(val message: String): TracksListEvents
}