package com.quoc.coroutine.domain.repository

import com.quoc.coroutine.domain.model.ImageModel
import com.quoc.coroutine.domain.param.LoadType
import com.quoc.coroutine.domain.lib.Resource
import kotlinx.coroutines.flow.Flow

interface ImageRepository : BaseRepository {

    suspend fun getImages(type: LoadType): Flow<Resource<List<ImageModel>>>

    suspend fun getImageDetail(id: String): Flow<Resource<ImageModel>>
}