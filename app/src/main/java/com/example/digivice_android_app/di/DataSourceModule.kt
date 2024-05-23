package com.example.digivice_android_app.di

import com.example.digivice_android_app.data.home.dataSources.HomeLocalDataSource
import com.example.digivice_android_app.data.home.dataSources.HomeRemoteDataSource
import com.example.digivice_android_app.data.login.dataSources.LoginLocalDataSource
import com.example.digivice_android_app.framework.data.implementations.home.HomeLocalDataSourceImpl
import com.example.digivice_android_app.framework.data.implementations.home.HomeRemoteDataSourceImpl
import com.example.digivice_android_app.framework.data.implementations.login.LoginLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun provideLoginLocalDataSource(
        dataSourceImpl: LoginLocalDataSourceImpl
    ): LoginLocalDataSource

    @Binds
    abstract fun provideHomeRemoteDataSource(
        dataSourceImpl: HomeRemoteDataSourceImpl
    ): HomeRemoteDataSource

    @Binds
    abstract fun provideHomeLocalDataSource(
        dataSourceImpl: HomeLocalDataSourceImpl
    ): HomeLocalDataSource
}