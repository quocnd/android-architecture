package com.quoc.coroutine.ui.detail

import androidx.lifecycle.viewModelScope
import com.quoc.coroutine.base.BaseViewModel
import com.quoc.coroutine.domain.model.ImageModel
import com.quoc.coroutine.domain.lib.Resource
import com.quoc.coroutine.domain.usecase.GetImageDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getImageDetailUseCase: GetImageDetailUseCase,
) : BaseViewModel() {

    private val _imageDetail = MutableStateFlow(ImageModel.createEmpty())
    val imageDetail: StateFlow<ImageModel>
        get() = _imageDetail

    fun getDetail(id: String) {
        viewModelScope.launch {
            getImageDetailUseCase.invoke(id)
                .collect {
                    when (it) {
                        is Resource.Success -> {
                            _imageDetail.value = it.data
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