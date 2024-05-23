package com.example.digivice_android_app.usecases

import com.example.digivice_android_app.data.home.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserNameUseCase @Inject constructor(
    private val repository: HomeRepository
) {

    suspend fun invoke(): Flow<String> {
        return repository.getUserName()
    }
}