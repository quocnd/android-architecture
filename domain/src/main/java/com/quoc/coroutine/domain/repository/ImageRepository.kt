package com.quoc.coroutine.domain.repository

import com.quoc.coroutine.domain.entity.ImageEntity
import com.quoc.coroutine.domain.param.LoadType
import com.quoc.coroutine.domain.lib.Resource
import kotlinx.coroutines.flow.Flow

interface ImageRepository : BaseRepository {

    suspend fun getImages(type: LoadType): Flow<Resource<List<ImageEntity>>>

    suspend fun getImageDetail(id: String): Flow<Resource<ImageEntity>>
}