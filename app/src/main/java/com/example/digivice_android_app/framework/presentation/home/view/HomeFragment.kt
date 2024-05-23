package com.example.digivice_android_app.framework.presentation.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.digivice_android_app.R
import com.example.digivice_android_app.databinding.FragmentHomeBinding
import com.example.digivice_android_app.framework.presentation.home.view.adapter.DigimonOverviewAdapter
import com.example.digivice_android_app.domain.Digimon
import com.example.digivice_android_app.framework.extensions.finishOnBackPressed
import com.example.digivice_android_app.framework.extensions.navigateTo
import com.example.digivice_android_app.framework.extensions.showShortMessage
import com.example.digivice_android_app.framework.extensions.viewBinding
import com.example.digivice_android_app.framework.presentation.home.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), DigimonOverviewAdapter.OnDigimonClickListener {

    private val binding by viewBinding { FragmentHomeBinding.inflate(layoutInflater) }

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var digimonsAdapter: DigimonOverviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        init()
        viewModel.setUp()
    }

    private fun init() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.default_spiner_item,
            resources.getStringArray(R.array.spinner_level_items)
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.levelSpinner.adapter = adapter

        setListeners()
    }

    private fun setObservers() = with(viewModel) {
        showLoader.observe(viewLifecycleOwner) { handleProgressVisibility(it) }
        digimonList.observe(viewLifecycleOwner) { setUpDigimonList(it) }
        userName.observe(viewLifecycleOwner) { handleUserNameTopMessage(it) }
        showErrorState.observe(viewLifecycleOwner) { showErrorState() }
        showAllList.observe(viewLifecycleOwner) { showAllDigimonList() }
        showFilter.observe(viewLifecycleOwner) { showFilteredList(it) }
        navigationEvent.observe(viewLifecycleOwner) { navigateTo(it) }
    }

    private fun setListeners() = with(binding) {
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                digimonsAdapter.filterDigimonByName(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })

        searchView.setOnCloseListener {
            digimonsAdapter.showAllDigimons()
            return@setOnCloseListener true
        }

        levelSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                viewModel.validateSpinnerPosition(pos, parent?.getItemAtPosition(pos).toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        finishOnBackPressed()
    }

    private fun showAllDigimonList() {
        digimonsAdapter.showAllDigimons()
    }

    private fun showFilteredList(currentFilter: String) {
        digimonsAdapter.filterDigimonsByLevel(currentFilter)
        showShortMessage(getString(R.string.order_message, currentFilter))
    }

    private fun setUpDigimonList(digimonList: List<Digimon>) {
        digimonsAdapter = DigimonOverviewAdapter(digimonList)
        digimonsAdapter.listener = this

        with(binding.digimonList) {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = digimonsAdapter
        }
    }

    private fun handleUserNameTopMessage(username: String) {
        binding.homeToolbar.title = getString(R.string.home_welcome_message, username)
    }

    private fun handleProgressVisibility(show: Boolean) = with(binding) {
        progress.isVisible = show
        containerItems.isVisible = !show
    }

    private fun showErrorState() = with(binding) {
        containerItems.isVisible = false
        errorStateDescription.isVisible = true
        errorStateImage.isVisible = true
    }

    override fun onDigimonPressed(digimonSelected: Digimon) {
        viewModel.navigateToDigimonDetail(digimonSelected)
    }
}