package com.quoc.coroutine.domain.repository

import com.quoc.coroutine.data.api.ApiService
import com.quoc.coroutine.data.api.NetworkConst
import com.quoc.coroutine.data.api.response.ImageResponse
import com.quoc.coroutine.data.db.ImageDao
import com.quoc.coroutine.data.storage.PreferencesDataStore
import com.quoc.coroutine.domain.lib.NetworkBoundResource
import com.quoc.coroutine.domain.lib.Resource
import com.quoc.coroutine.domain.model.ImageModel
import com.quoc.coroutine.domain.model.toModel
import com.quoc.coroutine.domain.param.LoadType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imageDao: ImageDao,
    private val dataStore: PreferencesDataStore,
    private val apiService: ApiService
) : ImageRepository {

    override suspend fun getImages(type: LoadType): Flow<Resource<List<ImageModel>>> {
        var page = dataStore.nextPage
            .catch { emit(NetworkConst.PAGING_STARTING_INDEX) }
            .first()

        return object : NetworkBoundResource<List<ImageResponse>, List<ImageModel>>() {

            override suspend fun preFetch(data: List<ImageModel>?) {
                if (type == LoadType.REFRESH || data.isNullOrEmpty()) {
                    page = NetworkConst.PAGING_STARTING_INDEX
                }
            }

            override suspend fun shouldFetch(data: List<ImageModel>?): Boolean {
                return if (type == LoadType.INITIAL) {
                    data.isNullOrEmpty()
                } else {
                    true
                }
            }

            override suspend fun saveCallResult(result: List<ImageResponse>) {
                imageDao.insertAll(result.map { it.toEntity() })
                dataStore.setPage(++page)
            }

            override suspend fun loadFromDb(): List<ImageModel> {
                return imageDao.getAllImages().map { it.toModel() }
            }

            override suspend fun shouldClearCurrentData(): Boolean {
                return page == NetworkConst.PAGING_STARTING_INDEX
            }

            override suspend fun clearCurrentData() {
                imageDao.deleteAll()
            }

            override suspend fun createCall(): List<ImageResponse> {
                return apiService.getNextImages(page, NetworkConst.PAGING_SIZE)
            }

        }.execute()
    }


    override suspend fun getImageDetail(id: String): Flow<Resource<ImageModel>> {

        return object : NetworkBoundResource<ImageResponse, ImageModel>() {

            override suspend fun shouldFetch(data: ImageModel?): Boolean {
                return data == null
            }

            override suspend fun loadFromDb(): ImageModel {
                return imageDao.getImage(id).toModel()
            }

            override suspend fun createCall(): ImageResponse {
                return apiService.detail(id)
            }

            override suspend fun saveCallResult(result: ImageResponse) {
                imageDao.insert(result.toEntity())
            }

        }.execute()

    }

}