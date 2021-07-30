package com.quoc.coroutine.ui.home

import androidx.lifecycle.viewModelScope
import com.quoc.coroutine.base.BaseViewModel
import com.quoc.coroutine.domain.model.ImageModel
import com.quoc.coroutine.domain.lib.Resource
import com.quoc.coroutine.domain.param.LoadType
import com.quoc.coroutine.domain.usecase.GetImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase
) : BaseViewModel() {

    private val _images = MutableStateFlow<List<ImageModel>>(emptyList())
    val images: StateFlow<List<ImageModel>> = _images

    fun getImages(type: LoadType) {
        viewModelScope.launch {
            getImagesUseCase.invoke(type)
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