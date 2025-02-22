package com.example.musicapp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.musicapp.domain.models.TrackEntity

class TrackDiffCallBack : DiffUtil.ItemCallback<TrackEntity>() {
    override fun areItemsTheSame(oldItem: TrackEntity, newItem: TrackEntity): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TrackEntity, newItem: TrackEntity): Boolean =
        oldItem == newItem
}
