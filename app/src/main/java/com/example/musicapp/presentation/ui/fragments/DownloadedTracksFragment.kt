package com.example.musicapp.presentation.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapp.R
import com.example.musicapp.databinding.FragmentDownloadedTracksBinding
import com.example.musicapp.domain.models.TrackEntity
import com.example.musicapp.presentation.adapters.TracksAdapter
import com.example.musicapp.presentation.application.MusicApp
import com.example.musicapp.presentation.viewmodels.DownloadedTracksViewModel
import com.example.musicapp.presentation.viewmodels.ViewModelFactory
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class DownloadedTracksFragment : Fragment() {

    private var _binding: FragmentDownloadedTracksBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: DownloadedTracksViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[DownloadedTracksViewModel::class.java]
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
        _binding = FragmentDownloadedTracksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeTracks()
        binding.navigationContainer.findViewById<View>(R.id.nav_home).setOnClickListener {
            findNavController().navigate(R.id.action_downloadedTracksFragment_to_tracksListFragment)
        }
    }

    private fun injectDependencies() {
        (requireActivity().application as MusicApp).component.inject(this)
    }

    private fun setupRecyclerView() {
        binding.tracksRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tracksAdapter
        }
    }

    private fun observeTracks() {
        viewModel.tracks
            .onEach { tracks -> tracksAdapter.submitList(tracks) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun onTrackClicked(track: TrackEntity) {
    }

    private fun onTrackLongClicked(track: TrackEntity) {
        viewModel.removeFromDownloaded(track)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
