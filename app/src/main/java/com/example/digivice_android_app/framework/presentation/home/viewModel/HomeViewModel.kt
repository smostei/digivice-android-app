package com.example.digivice_android_app.framework.presentation.home.viewModel

import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digivice_android_app.R
import com.example.digivice_android_app.domain.Digimon
import com.example.digivice_android_app.domain.NavigationEvent
import com.example.digivice_android_app.domain.Result
import com.example.digivice_android_app.domain.SingleLiveEvent
import com.example.digivice_android_app.framework.constants.DigimonAppConstants
import com.example.digivice_android_app.framework.presentation.home.viewModel.uiMapper.DigimonDomainToUiMapper
import com.example.digivice_android_app.usecases.GetDigimonListUseCase
import com.example.digivice_android_app.usecases.GetUserNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDigimonListUseCase: GetDigimonListUseCase,
    private val getUserNameUseCase: GetUserNameUseCase
): ViewModel() {

    private val _digimonListMLD = MutableLiveData<List<Digimon>>()
    val digimonList: LiveData<List<Digimon>> get() = _digimonListMLD

    private val _showLoaderMLD = MutableLiveData<Boolean>()
    val showLoader: LiveData<Boolean> get() = _showLoaderMLD

    private val _userNameMLD = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userNameMLD

    private val _showErrorStateMLD = MutableLiveData<Unit>()
    val showErrorState: LiveData<Unit> get() = _showErrorStateMLD

    private val _navigationEvent = SingleLiveEvent<NavigationEvent>()
    val navigationEvent: LiveData<NavigationEvent> get() = _navigationEvent

    private val _showAllList = MutableLiveData<Unit>()
    val showAllList: LiveData<Unit> get() = _showAllList

    private val _showFilter = MutableLiveData<String>()
    val showFilter: LiveData<String> get() = _showFilter

    fun setUp() {
        fetchDigimonList()
        getUserName()
    }

    private fun getUserName() {
        viewModelScope.launch {
            getUserNameUseCase.invoke().collect {
                _userNameMLD.postValue(it)
            }
        }
    }

    private fun fetchDigimonList() {
        _digimonListMLD.value?.let {
            _digimonListMLD.postValue(it)
        } ?: run {
            viewModelScope.launch {
                getDigimonListUseCase
                    .invoke()
                    .collect { result ->
                        when(result) {
                            is Result.Loading -> _showLoaderMLD.postValue(true)
                            is Result.Success -> {
                                _showLoaderMLD.postValue(false)
                                result.data.let { digimonList ->
                                    if(digimonList.isNotEmpty()) {
                                        _digimonListMLD.postValue(digimonList)
                                    } else {
                                        _showErrorStateMLD.postValue(Unit)
                                    }
                                }
                            }
                            is Result.Error -> {
                                _showLoaderMLD.postValue(false)
                                _showErrorStateMLD.postValue(Unit)
                            }
                        }
                    }
            }
        }
    }

    fun validateSpinnerPosition(index: Int, level: String) {
        if(index > DigimonAppConstants.DEFAULT_INDEX) {
            _showFilter.value = level
        } else {
            _showAllList.value = Unit
        }
    }

    fun navigateToDigimonDetail(digimonSelected: Digimon) {
        _navigationEvent.value = NavigationEvent(
            navigationActionId = R.id.action_homeFragment_to_digimonDetailFragment,
            bundle = bundleOf(
                DigimonAppConstants.DIGIMON_EXTRA to DigimonDomainToUiMapper.map(digimonSelected)
            )
        )
    }

}