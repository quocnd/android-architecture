package com.quoc.coroutine.data.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences_data_store")

class PreferencesDataStore @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    companion object {
        private val PREF_NEXT_PAGE = intPreferencesKey("pref_next_page")
    }

    val nextPage: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[PREF_NEXT_PAGE] ?: 1
        }

    suspend fun setCounter(nextPage: Int) {
        context.dataStore.edit { settings ->
            settings[PREF_NEXT_PAGE] = nextPage
        }
    }
}