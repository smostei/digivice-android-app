package com.example.digivice_android_app.framework.presentation.splash.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.digivice_android_app.databinding.FragmentSplashBinding
import com.example.digivice_android_app.framework.extensions.navigateTo
import com.example.digivice_android_app.framework.extensions.viewBinding
import com.example.digivice_android_app.framework.presentation.splash.viewModel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private val binding by viewBinding {
        FragmentSplashBinding.inflate(layoutInflater)
    }

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObservers()
        viewModel.setUp()
    }

    private fun addObservers() {
        viewModel.navigationEvent.observe(viewLifecycleOwner) { navigateTo(it) }
    }
}