package com.example.digivice_android_app.framework.presentation.splash.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digivice_android_app.R
import com.example.digivice_android_app.domain.NavigationEvent
import com.example.digivice_android_app.domain.SingleLiveEvent
import com.example.digivice_android_app.framework.constants.DigimonAppConstants
import com.example.digivice_android_app.usecases.IsSessionOnGoingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val isSessionOnGoingUseCase: IsSessionOnGoingUseCase
): ViewModel() {

    private val _navigationEvent = SingleLiveEvent<NavigationEvent>()
    val navigationEvent: LiveData<NavigationEvent> get() = _navigationEvent

    fun setUp() = flow {
        delay(DigimonAppConstants.SPLASH_TIME)
        emit(Unit)
    }.flowOn(Dispatchers.IO).onEach {
        onSplashFinished()
    }.launchIn(viewModelScope)


    private fun onSplashFinished() {
        _navigationEvent.value = when (isSessionOnGoingUseCase.invoke()) {
            true -> NavigationEvent(R.id.action_splashFragment_to_HomeFragment)
            false -> NavigationEvent(R.id.action_splashFragment_to_LoginFragment)
        }
    }
}