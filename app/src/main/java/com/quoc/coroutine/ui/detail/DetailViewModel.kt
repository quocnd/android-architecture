package com.quoc.coroutine.ui.detail

import androidx.lifecycle.viewModelScope
import com.quoc.coroutine.base.BaseViewModel
import com.quoc.coroutine.domain.entity.ImageEntity
import com.quoc.coroutine.domain.usecase.GetImageDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getImageDetailUseCase: GetImageDetailUseCase,
) : BaseViewModel() {

    private val _imageDetail = MutableStateFlow(ImageEntity.createEmpty())
    val imageDetail: StateFlow<ImageEntity>
        get() = _imageDetail

    fun getDetail(id: String){
        viewModelScope.launch {
            getImageDetailUseCase.execute(id)
                .catch {
                    handleError(it)
                }
                .collect {
                    _imageDetail.value = it
                }
        }

    }

}