package com.example.digivice_android_app.domain

sealed class Result<out T> {

    data class Success<out T>(val data: T): Result<T>() {
        override fun succeeded(): Boolean = true
    }

    data class Error(val error: Throwable): Result<Nothing>()

    object Loading: Result<Nothing>()

    open fun succeeded(): Boolean = false
}