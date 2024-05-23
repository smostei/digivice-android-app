package com.example.digivice_android_app.usecases

import com.example.digivice_android_app.data.home.HomeRepository
import com.example.digivice_android_app.domain.Digimon
import com.example.digivice_android_app.domain.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDigimonListUseCase @Inject constructor(
    private val repository: HomeRepository
) {

    suspend fun invoke(): Flow<Result<List<Digimon>>> {
        return repository.getAllDigimons()
    }
}