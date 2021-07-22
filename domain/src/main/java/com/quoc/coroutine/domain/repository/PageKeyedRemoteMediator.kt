package com.quoc.coroutine.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.quoc.coroutine.data.api.ApiService
import com.quoc.coroutine.data.api.NetworkConst
import com.quoc.coroutine.data.data.ImageData
import com.quoc.coroutine.data.db.ImageDao
import com.quoc.coroutine.domain.entity.ImageEntity
import com.quoc.coroutine.data.storage.PreferencesDataStore
import com.quoc.coroutine.domain.entity.toEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@ExperimentalPagingApi
class PageKeyedRemoteMediator(
    private val imageDao: ImageDao,
    private val dataStore: PreferencesDataStore,
    private val apiService: ApiService
) : RemoteMediator<Int, ImageData>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ImageData>
    ): MediatorResult {
        try {
            var loadKey = when (loadType) {
                LoadType.REFRESH -> NetworkConst.PAGING_STARTING_INDEX
                LoadType.PREPEND -> return MediatorResult.Success(true)
                LoadType.APPEND -> {
                    runBlocking { dataStore.nextPage.first() }
                }
            }
            val items = apiService.getNextImages(
                page = loadKey,
                limit = when (loadType) {
                    LoadType.REFRESH -> state.config.initialLoadSize
                    else -> state.config.pageSize
                }
            )

            if (loadType == LoadType.REFRESH) {
                imageDao.deleteAll()
            }
            imageDao.insertAll(items)
            dataStore.setCounter(++loadKey)

            return MediatorResult.Success(items.isEmpty())
        } catch (exception: Exception) {
            exception.printStackTrace()
            return MediatorResult.Error(exception)
        }
    }
}