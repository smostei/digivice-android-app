package com.example.digivice_android_app.usecases

import com.example.digivice_android_app.data.login.LoginRepository
import com.example.digivice_android_app.domain.Result
import com.example.digivice_android_app.framework.extensions.doLet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserLoginUseCase @Inject constructor(
    private val repository: LoginRepository
) {

    suspend fun invoke(username: String?, password: String?): Flow<Result<Unit>> {
        return doLet(username, password) { user, pass ->
            repository.logUserSession(user, pass)
        } ?: run {
            flow {
                emit(Result.Error(Exception("username or password cannot be null")))
            }
        }
    }
}