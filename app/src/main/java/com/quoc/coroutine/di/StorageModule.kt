package com.quoc.coroutine.di

import android.content.Context
import com.quoc.coroutine.data.storage.PreferencesDataStore
import com.quoc.coroutine.data.storage.SecuredSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Provides
    fun providePreferencesStoreData(@ApplicationContext context: Context): PreferencesDataStore {
        return PreferencesDataStore(context)
    }

    @Provides
    fun provideSecuredSharedPreferences(@ApplicationContext context: Context): SecuredSharedPreferences {
        return SecuredSharedPreferences(context)
    }


}