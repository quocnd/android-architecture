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
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase
) : BaseViewModel() {

    private val _images = MutableStateFlow<List<ImageEntity>>(emptyList())
    val images: StateFlow<List<ImageEntity>> = _images

    fun getImages() {
        viewModelScope.launch {
            getImagesUseCase.invoke(Any())
                .onStart { showLoading() }
                .collect {
                    hideLoading()
                    when (it) {
                        is Result.Success -> {
                            _images.value = it.data
                        }
                        is Result.Error -> {
                            handleError(it.exception)
                        }
                        else -> {
                        }
                    }
                }
        }
    }
}