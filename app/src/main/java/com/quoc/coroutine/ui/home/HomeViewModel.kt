package com.quoc.coroutine.ui.home

import androidx.lifecycle.viewModelScope
import com.quoc.coroutine.base.BaseViewModel
import com.quoc.coroutine.domain.entity.ImageEntity
import com.quoc.coroutine.domain.lib.Resource
import com.quoc.coroutine.domain.usecase.GetImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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
                .onCompletion { hideLoading() }
                .collect {
                    when (it) {
                        is Resource.Success -> {
                            _images.value = it.data
                        }
                        is Resource.Error -> {
                            handleError(it.exception)
                        }
                        else -> {
                        }
                    }
                }
        }
    }
}