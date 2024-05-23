package com.example.digivice_android_app.data.login

import com.example.digivice_android_app.data.login.dataSources.LoginLocalDataSource
import com.example.digivice_android_app.domain.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val dataSource: LoginLocalDataSource
) {

    suspend fun logUserSession(username: String, password: String): Flow<Result<Unit>> {
        return dataSource.logUserSession(username, password)
    }

    fun isSessionOnGoing(): Boolean {
        return dataSource.isSessionOnGoing()
    }
}