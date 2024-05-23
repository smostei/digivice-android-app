package com.example.digivice_android_app.framework.data.implementations.home

import com.example.digivice_android_app.data.home.dataSources.HomeRemoteDataSource
import com.example.digivice_android_app.domain.Digimon
import com.example.digivice_android_app.domain.Result
import com.example.digivice_android_app.framework.data.config.retrofit.DigimonApiService
import com.example.digivice_android_app.framework.data.implementations.home.mappers.DigimonResponseToDomainMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeRemoteDataSourceImpl @Inject constructor(
    private val digimonApiService: DigimonApiService,
    private val dispatcher: CoroutineDispatcher
): HomeRemoteDataSource {

    override suspend fun getAllDigimons(): Flow<Result<List<Digimon>>> = flow {
        emit(Result.Loading)
        val result = digimonApiService.getAllDigimons()
        emit(Result.Success(DigimonResponseToDomainMapper.map(result)))
    }.catch {
        emit(Result.Error(it))
    }.flowOn(dispatcher)

}