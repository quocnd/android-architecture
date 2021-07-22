package com.quoc.coroutine.domain.usecase

import androidx.paging.PagingData
import com.quoc.coroutine.data.data.ImageData
import com.quoc.coroutine.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(
    private val repository: ImageRepository
) {

    suspend fun execute(): Flow<PagingData<ImageData>>{
        return repository.getImages()
    }
}