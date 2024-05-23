package com.example.digivice_android_app.data.login.dataSources

import com.example.digivice_android_app.domain.Result
import kotlinx.coroutines.flow.Flow

interface LoginLocalDataSource {

    suspend fun logUserSession(username: String, password: String): Flow<Result<Unit>>

    fun isSessionOnGoing(): Boolean
}