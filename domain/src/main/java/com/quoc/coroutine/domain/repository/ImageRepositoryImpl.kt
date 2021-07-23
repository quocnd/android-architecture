package com.quoc.coroutine.domain.repository

import com.quoc.coroutine.data.api.ApiService
import com.quoc.coroutine.data.api.NetworkConst
import com.quoc.coroutine.data.data.ImageData
import com.quoc.coroutine.data.db.ImageDao
import com.quoc.coroutine.data.storage.PreferencesDataStore
import com.quoc.coroutine.domain.entity.ImageEntity
import com.quoc.coroutine.domain.entity.toEntity
import com.quoc.coroutine.domain.lib.NetworkBoundResource
import com.quoc.coroutine.domain.lib.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imageDao: ImageDao,
    private val dataStore: PreferencesDataStore,
    private val apiService: ApiService
) : ImageRepository {

    override suspend fun getImages(): Flow<Result<List<ImageEntity>>> {
        var page = dataStore.nextPage
            .catch { emit(NetworkConst.PAGING_STARTING_INDEX) }
            .first()

        return object : NetworkBoundResource<List<ImageData>, List<ImageEntity>>() {

            override suspend fun preFetch(data: List<ImageEntity>?) {
                if (data.isNullOrEmpty()) {
                    page = NetworkConst.PAGING_STARTING_INDEX
                }
            }

            override suspend fun saveCallResult(result: List<ImageData>) {
                imageDao.insertAll(result)
                dataStore.setPage(++page)
            }

            override suspend fun loadFromDb(): List<ImageEntity> {
                return imageDao.getAllImages().map { it.toEntity() }
            }

            override suspend fun shouldClearCurrentData(): Boolean {
                return page == NetworkConst.PAGING_STARTING_INDEX
            }

            override suspend fun clearCurrentData() {
                imageDao.deleteAll()
            }

            override suspend fun createCall(): List<ImageData> {
                return apiService.getNextImages(page, NetworkConst.PAGING_SIZE)
            }

        }.execute()
    }


    override suspend fun getImageDetail(id: String): Flow<Result<ImageEntity>> {

        return object : NetworkBoundResource<ImageData, ImageEntity>() {

            override suspend fun shouldFetch(data: ImageEntity?): Boolean {
                return data == null
            }

            override suspend fun loadFromDb(): ImageEntity {
                return imageDao.getImage(id).toEntity()
            }

            override suspend fun createCall(): ImageData {
                return apiService.detail(id)
            }

            override suspend fun saveCallResult(result: ImageData) {
                imageDao.insert(result)
            }

        }.execute()

    }

}