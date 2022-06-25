package com.example.a20220624_movieapplication_juanmartinezleon.cons

sealed class UiState {
    data class Success(val data: Any) : UiState()
    data class Error(val error: Exception): UiState()
    object Loading : UiState()

}