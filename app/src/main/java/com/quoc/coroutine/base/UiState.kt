package com.quoc.coroutine.base

data class UiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isUnauthorized: Boolean = false,
    val error: String? = null,
)
