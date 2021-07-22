package com.quoc.coroutine.domain.usecase

import com.quoc.coroutine.domain.entity.ImageEntity
import com.quoc.coroutine.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImageDetailUseCase @Inject constructor(
    private val repository: ImageRepository
) {

    suspend fun execute(id: String): Flow<ImageEntity>{
        return repository.getImageDetail(id)
    }
}