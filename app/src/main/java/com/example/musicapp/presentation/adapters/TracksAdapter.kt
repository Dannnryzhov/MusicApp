package com.example.musicapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapp.databinding.TrackItemBinding
import com.example.musicapp.domain.models.TrackEntity

class TracksAdapter(
    private val onItemClick: (TrackEntity) -> Unit,
    private val onItemLongClick: (TrackEntity) -> Unit
) : ListAdapter<TrackEntity, TracksAdapter.TrackViewHolder>(TrackDiffCallBack()) {

    private var downloadedTrackIds: Set<Long> = emptySet()

    fun updateDownloadedTracks(downloaded: List<TrackEntity>) {
        val newDownloadedTrackIds = downloaded.map { it.id }.toSet()
        currentList.forEachIndexed { index, track ->
            val wasDownloaded = downloadedTrackIds.contains(track.id)
            val isDownloaded = newDownloadedTrackIds.contains(track.id)
            if (wasDownloaded != isDownloaded) {
                notifyItemChanged(index)
            }
        }
        downloadedTrackIds = newDownloadedTrackIds
    }

    override fun getItemId(position: Int): Long = getItem(position).id

    inner class TrackViewHolder(private val binding: TrackItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(track: TrackEntity) {
            binding.trackTitle.text = track.title
            binding.trackArtist.text = track.artist.name

            Glide.with(binding.trackPicture.context)
                .load(track.album.cover)
                .centerCrop()
                .into(binding.trackPicture)

            binding.trackExtraIcon.visibility = if (downloadedTrackIds.contains(track.id)) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }

            binding.root.setOnClickListener { onItemClick(track) }
            binding.root.setOnLongClickListener {
                onItemLongClick(track)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding = TrackItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TrackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

