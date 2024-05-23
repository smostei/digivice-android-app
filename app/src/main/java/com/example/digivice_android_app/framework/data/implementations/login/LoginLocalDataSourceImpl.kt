package com.example.digivice_android_app.framework.data.implementations.login

import com.example.digivice_android_app.data.login.dataSources.LoginLocalDataSource
import com.example.digivice_android_app.domain.Result
import com.example.digivice_android_app.domain.User
import com.example.digivice_android_app.framework.utils.SharedPreferencesManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.IllegalArgumentException
import javax.inject.Inject
import kotlin.random.Random

class LoginLocalDataSourceImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val sharedPreferencesManager: SharedPreferencesManager
): LoginLocalDataSource {

    // User list hardcoded in order to test login
    private val _users = listOf(
        User(id = Random.nextInt().toString(), username =  "santimostei", password = "1234")
    )

    override suspend fun logUserSession(username: String, password: String): Flow<Result<Unit>> = flow {
        emit(Result.Loading)
        delay(1500L)

        _users.find { it.username == username && it.password == password }
            ?: throw IllegalArgumentException("user can't be null")

        sharedPreferencesManager.apply {
            setUserLogged(true)
            setUsernamePreference(username)
        }

        emit(Result.Success(Unit))
    }.catch {
        emit(Result.Error(it))
    }.flowOn(dispatcher)

    override fun isSessionOnGoing(): Boolean {
        return sharedPreferencesManager.isUserLogged()
    }

}