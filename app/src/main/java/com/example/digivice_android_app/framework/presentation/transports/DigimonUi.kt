package com.example.digivice_android_app.framework.presentation.transports

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DigimonUi(
    val name: String,
    val image: String,
    val level: String
): Parcelable