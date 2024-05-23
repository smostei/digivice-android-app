package com.example.digivice_android_app.data.home.dataSources

import com.example.digivice_android_app.domain.Digimon
import com.example.digivice_android_app.domain.Result
import kotlinx.coroutines.flow.Flow

interface HomeRemoteDataSource {

    suspend fun getAllDigimons(): Flow<Result<List<Digimon>>>
}