package com.example.digivice_android_app.framework.presentation.digimonDetail.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.digivice_android_app.databinding.FragmentDigimonDetailBinding
import com.example.digivice_android_app.framework.extensions.back
import com.example.digivice_android_app.framework.extensions.viewBinding
import com.example.digivice_android_app.framework.presentation.digimonDetail.viewModel.DigimonDetailViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DigimonDetailFragment : Fragment() {

    private val binding by viewBinding { FragmentDigimonDetailBinding.inflate(layoutInflater) }

    private val viewModel: DigimonDetailViewModel by viewModels()

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
        setListeners()
        viewModel.setUp(arguments)
    }

    private fun addObservers() = with(viewModel) {
        digimonName.observe(viewLifecycleOwner) { setDigimonName(it) }
        digimonLevel.observe(viewLifecycleOwner) { setDigimonLevel(it) }
        digimonImageUrl.observe(viewLifecycleOwner) { setDigimonImage(it) }
        navigateBack.observe(viewLifecycleOwner) { back() }
    }

    private fun setListeners() {
        binding.digimonDetailToolbar.setNavigationOnClickListener { back() }
    }

    private fun setDigimonName(name: String) {
        binding.digimonName.text = name
    }

    private fun setDigimonLevel(level: String) {
        binding.digimonLevel.text = level
    }

    private fun setDigimonImage(imageUrl: String) {
        Picasso
            .get()
            .load(imageUrl)
            .into(binding.digimonImage)
    }
}