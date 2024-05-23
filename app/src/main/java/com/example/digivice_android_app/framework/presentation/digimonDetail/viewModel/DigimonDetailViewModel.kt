package com.example.digivice_android_app.framework.presentation.digimonDetail.viewModel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.digivice_android_app.framework.constants.DigimonAppConstants
import com.example.digivice_android_app.framework.presentation.transports.DigimonUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DigimonDetailViewModel @Inject constructor(): ViewModel() {

    private var digimon: DigimonUi? = null

    private val _digimonName = MutableLiveData<String>()
    val digimonName: LiveData<String> get() = _digimonName

    private val _digimonLevel = MutableLiveData<String>()
    val digimonLevel: LiveData<String> get() = _digimonLevel

    private val _digimonImageUrl = MutableLiveData<String>()
    val digimonImageUrl: LiveData<String> get() = _digimonImageUrl

    private val _navigateBack = MutableLiveData<Unit>()
    val navigateBack: LiveData<Unit> get() = _navigateBack

    fun setUp(bundle: Bundle?) {
        digimon = bundle?.getParcelable(DigimonAppConstants.DIGIMON_EXTRA)
        buildScreen()
    }

    private fun buildScreen() {
        digimon?.let {
            _digimonName.value = it.name
            _digimonLevel.value = it.level
            _digimonImageUrl.value = it.image
        } ?: run {
            _navigateBack.value = Unit
        }
    }
}