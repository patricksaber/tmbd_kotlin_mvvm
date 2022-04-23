package com.neugelb.data

sealed class Resources<T>(val data: T? = null, val error: UiState.Error? = null) {
    class Success<T>(data: T) : Resources<T>(data)
    class Error<T>(error: UiState.Error, data: T? = null) : Resources<T>(data, error)
}
