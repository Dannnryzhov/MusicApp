package com.example.musicapp.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.musicapp.R
import com.example.musicapp.databinding.FragmentTracksListBinding
import com.example.musicapp.domain.models.TrackEntity
import com.example.musicapp.presentation.adapters.TracksAdapter
import com.example.musicapp.presentation.application.MusicApp
import com.example.musicapp.presentation.viewmodels.TracksListEvents
import com.example.musicapp.presentation.viewmodels.TracksListViewModel
import com.example.musicapp.presentation.viewmodels.ViewModelFactory
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class TracksListFragment : BaseFragment<FragmentTracksListBinding, TracksListViewModel>() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override val viewModel: TracksListViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[TracksListViewModel::class.java]
    }

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentTracksListBinding {
        return FragmentTracksListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            setupRecyclerView()
            observeTracks()
            setupSearch()
            observeEvents()
            observeDownloadedTracks()
    }

    private val tracksAdapter: TracksAdapter by lazy {
        TracksAdapter(
            onItemClick = { track -> onTrackClicked(track) },
            onItemLongClick = { track -> onTrackLongClicked(track) }
        )
    }

    override fun injectDependencies() {
        (requireActivity().application as MusicApp).component.inject(this)
    }

    private fun setupRecyclerView() {
        binding.tracksRecyclerView.adapter = tracksAdapter
    }

    private fun observeTracks() {
        viewModel.tracks
            .onEach { tracks -> tracksAdapter.submitList(tracks) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun observeDownloadedTracks() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.downloadedTracks.collect { downloaded ->
                tracksAdapter.updateDownloadedTracks(downloaded)
            }
        }
    }

    private fun observeEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventFlow.collect { event ->
                    when (event) {
                        is TracksListEvents.ShowTrackListDialog -> {
                            showTrackListDialog(event.message)
                        }
                    }
                }
            }
        }
    }

    private fun showTrackListDialog(message: String) {
        android.app.AlertDialog.Builder(requireContext(), R.style.MyAlertDialogStyle)
            .setTitle("Сообщение")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun setupSearch() {
        binding.searchEditText.addTextChangedListener { editable ->
            val query = editable?.toString() ?: ""
            viewModel.search(query)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchResults.collect { searchResults ->
                tracksAdapter.submitList(searchResults)
            }
        }
    }

    private fun onTrackClicked(track: TrackEntity) {
        viewModel.triggerTestError()
    }

    private fun onTrackLongClicked(track: TrackEntity) {
        viewModel.toggleDownloadedTrack(track)
    }
}
