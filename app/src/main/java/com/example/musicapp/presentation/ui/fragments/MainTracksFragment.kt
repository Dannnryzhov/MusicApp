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
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.R
import com.example.musicapp.databinding.FragmentMainTracksBinding
import com.example.musicapp.domain.usecases.ManageDownloadedTracksUseCase
import com.example.musicapp.presentation.adapters.TracksAdapter
import com.example.musicapp.presentation.application.MusicApp
import com.example.musicapp.presentation.viewmodels.MainTracksViewModel
import com.example.musicapp.presentation.viewmodels.SearchTracksViewModel
import com.example.musicapp.presentation.viewmodels.ViewModelFactory
import androidx.core.widget.addTextChangedListener
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainTracksFragment : Fragment() {

    private var _binding: FragmentMainTracksBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MainTracksViewModel

    private lateinit var searchViewModel: SearchTracksViewModel

    private lateinit var tracksAdapter: TracksAdapter

    @Inject
    lateinit var manageDownloadedTracksUseCase: ManageDownloadedTracksUseCase

    private var downloadedTrackIds = mutableSetOf<Long>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainTracksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().application as MusicApp).component.inject(this)

        viewModel = ViewModelProvider(this, viewModelFactory)[MainTracksViewModel::class.java]

        tracksAdapter = TracksAdapter(
            onItemClick = { track ->
                val action = MainTracksFragmentDirections
                    .actionMainTracksFragmentToPlayTrackFragment(track.title,
                        track.artist.name,
                        track.album.cover,
                        track.preview)
                findNavController().navigate(action)
            },
            onItemLongClick = { track ->
                lifecycleScope.launch {
                    if (downloadedTrackIds.contains(track.id)) {
                        manageDownloadedTracksUseCase.remove(track)
                    } else {
                        manageDownloadedTracksUseCase.add(track)
                    }
                }
            }
        )

        binding.tracksRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tracksAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    if (totalItemCount > 0 && lastVisibleItemPosition >= totalItemCount - 5) {
                        viewModel.fetchTracks()
                    }
                }
            })
        }
        searchViewModel = ViewModelProvider(this, viewModelFactory)
            .get(SearchTracksViewModel::class.java)
        binding.searchEditText.addTextChangedListener { editable ->
            val query = editable?.toString() ?: ""
            if (query.isNotBlank()) {
                searchViewModel.search(query)
            }
        }

        lifecycleScope.launch {
            manageDownloadedTracksUseCase.getDownloaded().collect { downloadedTracks ->
                downloadedTrackIds = downloadedTracks.map { it.id }.toMutableSet()
            }
        }

        lifecycleScope.launch {
            viewModel.tracks.collect { tracks ->
                tracksAdapter.submitList(tracks)
            }
        }

        binding.navigationContainer.findViewById<View>(R.id.nav_downloaded).setOnClickListener {
            findNavController().navigate(R.id.action_mainTracksFragment_to_downloadedTracksFragment)
        }

        binding.navigationContainer.findViewById<View>(R.id.nav_home).setOnClickListener {

        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
