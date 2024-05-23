package com.example.digivice_android_app.data.home

import com.example.digivice_android_app.data.home.dataSources.HomeLocalDataSource
import com.example.digivice_android_app.data.home.dataSources.HomeRemoteDataSource
import com.example.digivice_android_app.domain.Digimon
import com.example.digivice_android_app.domain.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val remoteDataSource: HomeRemoteDataSource,
    private val localDataSource: HomeLocalDataSource
) {

    suspend fun getAllDigimons(): Flow<Result<List<Digimon>>> {
        return remoteDataSource.getAllDigimons()
    }

    suspend fun getUserName(): Flow<String> {
        return localDataSource.getUserName()
    }
}