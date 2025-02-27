package com.example.musicapp.presentation.ui.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.musicapp.R
import com.example.musicapp.databinding.FragmentTracksListBinding
import com.example.musicapp.domain.models.TrackEntity
import com.example.musicapp.presentation.adapters.TracksAdapter
import com.example.musicapp.presentation.application.MusicApp
import com.example.musicapp.presentation.viewmodels.SearchTracksViewModel
import com.example.musicapp.presentation.viewmodels.TracksListViewModel
import com.example.musicapp.presentation.viewmodels.ViewModelFactory
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class TracksListFragment : Fragment() {

    private var _binding: FragmentTracksListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: TracksListViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[TracksListViewModel::class.java]
    }

    private val tracksAdapter: TracksAdapter by lazy {
        TracksAdapter(
            onItemClick = { track -> onTrackClicked(track) },
            onItemLongClick = { track -> onTrackLongClicked(track) }
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectDependencies()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTracksListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            setupRecyclerView()
            observeTracks()
        binding.navigationContainer.findViewById<View>(R.id.nav_downloaded).setOnClickListener {
            findNavController().navigate(R.id.action_tracksListFragment_to_downloadedTracksFragment)
        }
    }

    private fun injectDependencies() {
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

    private fun onTrackClicked(track: TrackEntity) {
    }

    private fun onTrackLongClicked(track: TrackEntity) {
        viewModel.toggleDownloadedTrack(track)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
