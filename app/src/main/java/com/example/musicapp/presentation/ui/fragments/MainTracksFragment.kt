package com.example.musicapp.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.FragmentMainTracksBinding
import com.example.musicapp.presentation.adapters.TracksAdapter
import com.example.musicapp.presentation.application.MusicApp
import com.example.musicapp.presentation.viewmodels.MainTracksViewModel
import com.example.musicapp.presentation.viewmodels.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainTracksFragment : Fragment() {

    private var _binding: FragmentMainTracksBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MainTracksViewModel
    private lateinit var tracksAdapter: TracksAdapter

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
            onItemClick = {
                TODO()
            },
            onItemLongClick = {
                TODO()
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


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.tracks.collect { tracks ->
                tracksAdapter.submitList(tracks)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
