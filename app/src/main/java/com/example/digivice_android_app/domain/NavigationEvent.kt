package com.example.digivice_android_app.domain

import android.os.Bundle
import androidx.annotation.AnyRes

data class NavigationEvent(
    @AnyRes val navigationActionId: Int,
    val bundle: Bundle? = null
)