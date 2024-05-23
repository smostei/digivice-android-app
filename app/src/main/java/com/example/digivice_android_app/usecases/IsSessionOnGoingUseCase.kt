package com.example.digivice_android_app.usecases

import com.example.digivice_android_app.data.login.LoginRepository
import javax.inject.Inject

class IsSessionOnGoingUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    fun invoke(): Boolean {
        return loginRepository.isSessionOnGoing()
    }
}