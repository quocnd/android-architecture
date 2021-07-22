package com.quoc.coroutine.ui.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.quoc.coroutine.base.BaseViewModel
import com.quoc.coroutine.data.data.ImageData
import com.quoc.coroutine.domain.entity.ImageEntity
import com.quoc.coroutine.domain.usecase.GetImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase
) : BaseViewModel() {

    private val _images = MutableStateFlow<PagingData<ImageData>>(PagingData.empty())
    val images: StateFlow<PagingData<ImageData>> = _images

    fun getImagesPaging(){
        showLoading()
        viewModelScope.launch {
            getImagesUseCase.execute()
                .catch {
                    handleError(it)
                }
                .cachedIn(viewModelScope)
                .collect{
                    _images.value = it
                }
            hideLoading()
        }

    }
}