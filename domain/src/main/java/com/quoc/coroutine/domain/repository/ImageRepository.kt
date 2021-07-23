package com.quoc.coroutine.domain.repository

import com.quoc.coroutine.domain.entity.ImageEntity
import com.quoc.coroutine.domain.lib.Result
import kotlinx.coroutines.flow.Flow

interface ImageRepository : BaseRepository {

    suspend fun getImages(): Flow<Result<List<ImageEntity>>>

    suspend fun getImageDetail(id: String): Flow<Result<ImageEntity>>
}