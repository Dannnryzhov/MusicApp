package com.example.musicapp.presentation.ui.fragments

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
import com.example.musicapp.presentation.adapters.TracksAdapter
import com.example.musicapp.presentation.application.MusicApp
import com.example.musicapp.presentation.viewmodels.DownloadedTracksViewModel
import com.example.musicapp.presentation.viewmodels.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class DownloadedTracksFragment : Fragment() {

    private var _binding: FragmentDownloadedTracksBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: DownloadedTracksViewModel
    private lateinit var tracksAdapter: TracksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDownloadedTracksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().application as MusicApp).component.inject(this)

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(DownloadedTracksViewModel::class.java)

        tracksAdapter = TracksAdapter(
            onItemClick = {
            },
            onItemLongClick = { track ->
                lifecycleScope.launch {
                    viewModel.removeFromDownloaded(track)
                }
            }
        )

        binding.tracksRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tracksAdapter
        }

        lifecycleScope.launch {
            viewModel.tracks.collect { tracks ->
                tracksAdapter.submitList(tracks)
            }
        }

        binding.navigationContainer.findViewById<View>(R.id.nav_home).setOnClickListener {
            findNavController().navigate(R.id.action_downloadedTracksFragment_to_mainTracksFragment)
        }
        binding.navigationContainer.findViewById<View>(R.id.nav_downloaded).setOnClickListener {
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
