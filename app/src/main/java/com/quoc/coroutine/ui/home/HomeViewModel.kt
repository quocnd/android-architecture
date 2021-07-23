package com.quoc.coroutine.ui.home

import androidx.lifecycle.viewModelScope
import com.quoc.coroutine.base.BaseViewModel
import com.quoc.coroutine.domain.entity.ImageEntity
import com.quoc.coroutine.domain.lib.Result
import com.quoc.coroutine.domain.usecase.GetImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase
) : BaseViewModel() {

    private val _imagesFlow = MutableStateFlow<List<ImageEntity>>(emptyList())
    val imagesFlow: StateFlow<List<ImageEntity>> = _imagesFlow

    fun getImagesFlow() {
        showLoading()
        viewModelScope.launch {
            getImagesUseCase.invoke(Any())
                .collect {
                    when (it) {
                        is Result.Success -> {
                            _imagesFlow.value = it.data
                        }
                        is Result.Error -> {
                            handleError(it.exception)
                        }
                        else -> {
                        }
                    }
                }
            hideLoading()
        }
    }
}