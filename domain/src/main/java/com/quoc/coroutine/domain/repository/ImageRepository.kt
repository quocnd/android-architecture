package com.quoc.coroutine.domain.repository

import androidx.paging.PagingData
import com.quoc.coroutine.data.data.ImageData
import com.quoc.coroutine.domain.entity.ImageEntity
import kotlinx.coroutines.flow.Flow

interface ImageRepository: BaseRepository {

    suspend fun getImages(): Flow<PagingData<ImageData>>

    suspend fun getImageDetail(id: String): Flow<ImageEntity>
}