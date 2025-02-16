package com.example.musicapp.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.musicapp.databinding.FragmentPlayTrackBinding

class PlayTrackFragment : Fragment() {

    private var _binding: FragmentPlayTrackBinding? = null
    private val binding get() = _binding!!

    private val args: PlayTrackFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayTrackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.trackTitleTextView.text = args.trackTitle
        binding.artistNameTextView.text = args.artistName
        Glide.with(this)
            .load(args.coverImage)
            .into(binding.coverImageView)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
