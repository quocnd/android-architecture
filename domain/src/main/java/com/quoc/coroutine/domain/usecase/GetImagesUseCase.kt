package com.quoc.coroutine.domain.usecase

import com.quoc.coroutine.domain.di.IoDispatcher
import com.quoc.coroutine.domain.model.ImageModel
import com.quoc.coroutine.domain.param.LoadType
import com.quoc.coroutine.domain.lib.Resource
import com.quoc.coroutine.domain.repository.ImageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(
    private val repository: ImageRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<LoadType, List<ImageModel>>(dispatcher) {

    override suspend fun execute(parameters: LoadType): Flow<Resource<List<ImageModel>>> {
        return repository
            .getImages(parameters)
    }


}