package com.example.digivice_android_app.data.home.dataSources

import kotlinx.coroutines.flow.Flow

interface HomeLocalDataSource {

    suspend fun getUserName(): Flow<String>
}
