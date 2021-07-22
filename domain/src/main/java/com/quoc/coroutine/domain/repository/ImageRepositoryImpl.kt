package com.quoc.coroutine.domain.repository

import androidx.paging.*
import com.quoc.coroutine.data.api.ApiService
import com.quoc.coroutine.data.api.NetworkConst
import com.quoc.coroutine.data.data.ImageData
import com.quoc.coroutine.data.db.ImageDao
import com.quoc.coroutine.domain.entity.ImageEntity
import com.quoc.coroutine.data.storage.PreferencesDataStore
import com.quoc.coroutine.domain.entity.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalPagingApi
class ImageRepositoryImpl @Inject constructor(
    private val imageDao: ImageDao,
    private val dataStore: PreferencesDataStore,
    private val apiService: ApiService
) : ImageRepository {

    override suspend fun getImages(): Flow<PagingData<ImageData>> {
        return Pager(
            config = PagingConfig(NetworkConst.PAGING_SIZE),
            remoteMediator = PageKeyedRemoteMediator(imageDao, dataStore, apiService)
        ) {
            val images = imageDao.getAll()
            images
        }.flow
    }

    override suspend fun getImageDetail(id: String): Flow<ImageEntity> {
        return flow {
            emit(apiService.detail(id).toEntity())
        }
    }

}