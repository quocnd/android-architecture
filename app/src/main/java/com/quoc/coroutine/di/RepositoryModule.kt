package com.quoc.coroutine.di

import androidx.paging.ExperimentalPagingApi
import com.quoc.coroutine.data.api.ApiService
import com.quoc.coroutine.data.db.ImageDao
import com.quoc.coroutine.domain.repository.ImageRepository
import com.quoc.coroutine.domain.repository.ImageRepositoryImpl
import com.quoc.coroutine.data.storage.PreferencesDataStore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

//    @ExperimentalPagingApi
//    @Binds
//    fun indImageRepository(
//        imageDao: ImageDao,
//        preferencesDataStore: PreferencesDataStore,
//        apiService: ApiService
//    ): ImageRepository =
//        ImageRepositoryImpl(imageDao, preferencesDataStore, apiService)

    @ExperimentalPagingApi
    @Binds
    abstract fun indImageRepository(
        imageRepositoryImpl: ImageRepositoryImpl
    ): ImageRepository

}