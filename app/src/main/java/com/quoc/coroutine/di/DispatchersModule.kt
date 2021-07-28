package com.quoc.coroutine.di

import com.quoc.coroutine.domain.di.DefaultDispatcher
import com.quoc.coroutine.domain.di.IoDispatcher
import com.quoc.coroutine.domain.di.MainDispatcher
import com.quoc.coroutine.domain.di.Unconfined
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class DispatchersModule {

    @Provides
    @DefaultDispatcher
    fun provideDefaultDispatcher() = Dispatchers.Default

    @Provides
    @IoDispatcher
    fun provideIoDispatcher() = Dispatchers.IO

    @Provides
    @MainDispatcher
    fun provideMainDispatcher() = Dispatchers.Main

    @Provides
    @Unconfined
    fun provideUnconfinedDispatcher() = Dispatchers.Unconfined
}