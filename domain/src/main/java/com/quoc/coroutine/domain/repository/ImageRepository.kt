package com.quoc.coroutine.domain.repository

import com.quoc.coroutine.domain.entity.ImageEntity
import com.quoc.coroutine.domain.lib.Resource
import kotlinx.coroutines.flow.Flow

interface ImageRepository : BaseRepository {

    suspend fun getImages(): Flow<Resource<List<ImageEntity>>>

    suspend fun getImageDetail(id: String): Flow<Resource<ImageEntity>>
}