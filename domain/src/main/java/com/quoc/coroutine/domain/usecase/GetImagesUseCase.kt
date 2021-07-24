package com.quoc.coroutine.domain.usecase

import com.quoc.coroutine.domain.di.IoDispatcher
import com.quoc.coroutine.domain.entity.ImageEntity
import com.quoc.coroutine.domain.lib.Resource
import com.quoc.coroutine.domain.repository.ImageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(
    private val repository: ImageRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<Any, List<ImageEntity>>(dispatcher) {

    override suspend fun execute(parameters: Any): Flow<Resource<List<ImageEntity>>> {
        return repository
            .getImages()
    }


}