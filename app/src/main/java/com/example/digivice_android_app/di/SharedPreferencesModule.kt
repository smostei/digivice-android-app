package com.example.digivice_android_app.di

import android.app.Application
import android.content.Context
import com.example.digivice_android_app.framework.utils.SharedPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    private const val PACKAGE = "com.example.digivice_android_app.framework.utils"

    @Provides
    fun provideSharedPreferencesManager(application: Application): SharedPreferencesManager {
        return SharedPreferencesManager(application.getSharedPreferences(PACKAGE, Context.MODE_PRIVATE))
    }
}