package com.example.digivice_android_app.framework.data.config.retrofit.response

import com.google.gson.annotations.SerializedName

data class DigimonResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("img")
    val image: String,
    @SerializedName("level")
    val level: String
)