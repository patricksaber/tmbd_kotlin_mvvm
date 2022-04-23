package com.neugelb.data

import com.neugelb.data.model.Person

data class UiState(
    val isLoading: Boolean = false,
    val error: Error? = null,
    val items: Person? = null
) {
    sealed class Error {
        object NetworkError : Error()
        object ServerError : Error()
        object Invalid : Error()
    }
}
