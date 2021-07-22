package com.quoc.coroutine.data.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SecuredSharedPreferences @Inject constructor
    (@ApplicationContext applicationContext: Context
) {

    companion object {
        private const val SHARED_PREFERENCES_FILE: String = "encrypted_shared_preferences"
        private const val COUNTER_KEY: String = "counter_key"
    }

    private val mainKey = MasterKey.Builder(applicationContext)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        applicationContext,
        SHARED_PREFERENCES_FILE,
        mainKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    var counter: Int
        get() = sharedPreferences.getInt(COUNTER_KEY, -1)
        set(value) {
            sharedPreferences.edit().putInt(COUNTER_KEY, value).apply()
        }

}