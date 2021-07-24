package com.quoc.coroutine.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import com.quoc.coroutine.lib.IsLoading
import com.quoc.coroutine.util.parseMessage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

open class BaseViewModel : ViewModel() {

    @Inject
    lateinit var context: Application

    private var loadingCount = 0

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<IsLoading>
        get() = _isLoading

    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String>
        get() = _error

    private val _isUnauthorized = MutableSharedFlow<Boolean>()
    val isUnauthorized: SharedFlow<Boolean>
        get() = _isUnauthorized

    protected fun showLoading() {
        if (loadingCount == 0) {
            _isLoading.value = true
        }
        ++loadingCount
    }

    fun hideLoading() {
        --loadingCount
        if (loadingCount == 0) {
            _isLoading.value = false
        }
    }

    protected suspend fun handleError(throwable: Throwable) {
        val (isUnauthorized, message) = throwable.parseMessage(context)
        if (isUnauthorized) {
            _isUnauthorized.emit(true)
        } else {
            _error.emit(message)
        }
    }

}