package com.example.musicapp.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.musicapp.R
import com.example.musicapp.databinding.FragmentHostBinding
import com.example.musicapp.presentation.application.MusicApp
import com.example.musicapp.presentation.viewmodels.HostViewModel
import com.example.musicapp.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject

class HostFragment : BaseFragment<FragmentHostBinding, HostViewModel>() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override val viewModel: HostViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[HostViewModel::class.java]
    }

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHostBinding {
        return FragmentHostBinding.inflate(inflater, container, false)
    }

    override fun injectDependencies() {
        (requireActivity().application as MusicApp).component.inject(this)
    }

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
    }
}
