package com.example.digivice_android_app.framework.presentation.login.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digivice_android_app.R
import com.example.digivice_android_app.domain.NavigationEvent
import com.example.digivice_android_app.domain.Result
import com.example.digivice_android_app.domain.SingleLiveEvent
import com.example.digivice_android_app.usecases.UserLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userLoginUseCase: UserLoginUseCase
): ViewModel() {

    private val _onLoginFailedMLD = MutableLiveData<Int>()
    val onLoginFailed: LiveData<Int> get() = _onLoginFailedMLD

    private val _showLoaderMLD = MutableLiveData<Boolean>()
    val showLoader: LiveData<Boolean> get() = _showLoaderMLD

    private val _onNavigationEvent = SingleLiveEvent<NavigationEvent>()
    val onNavigationEvent: LiveData<NavigationEvent> get() = _onNavigationEvent

    private val _clearInputError = MutableLiveData<Unit>()
    val clearInputError: LiveData<Unit> get() = _clearInputError

    fun getUser(username: String?, password: String?) {
        viewModelScope.launch {
            userLoginUseCase
                .invoke(username.orEmpty(), password.orEmpty())
                .collect { result ->
                    when (result) {
                        is Result.Loading -> _showLoaderMLD.value = true
                        is Result.Success -> {
                            _showLoaderMLD.value = false
                            _onNavigationEvent.value = NavigationEvent(R.id.action_loginFragment_to_homeFragment)
                        }

                        is Result.Error -> {
                            _showLoaderMLD.value = false
                            _onLoginFailedMLD.value = R.string.failure_hint
                        }
                    }
                }
        }
    }

    fun resetEditText(inputError: CharSequence?) {
        if(!inputError.isNullOrEmpty()) {
            _clearInputError.value = Unit
        }
    }
}