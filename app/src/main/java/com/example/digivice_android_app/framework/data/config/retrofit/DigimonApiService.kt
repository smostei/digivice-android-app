package com.example.digivice_android_app.framework.data.config.retrofit

import com.example.digivice_android_app.framework.data.config.retrofit.response.DigimonResponse
import retrofit2.http.GET

interface DigimonApiService {

    @GET("api/digimon")
    suspend fun getAllDigimons(): List<DigimonResponse>
}