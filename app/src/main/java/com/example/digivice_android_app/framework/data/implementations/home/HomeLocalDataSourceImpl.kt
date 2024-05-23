package com.example.digivice_android_app.framework.data.implementations.home

import com.example.digivice_android_app.data.home.dataSources.HomeLocalDataSource
import com.example.digivice_android_app.framework.utils.SharedPreferencesManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeLocalDataSourceImpl @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager
) : HomeLocalDataSource {

    override suspend fun getUserName(): Flow<String> = flow {
        emit(sharedPreferencesManager.getUsernamePreference().orEmpty())
    }
}
