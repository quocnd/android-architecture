package com.quoc.coroutine

import android.app.Application
//import com.jakewharton.threetenabp.AndroidThreeTen
import timber.log.Timber

/**
 * Used as a base application for Hilt to run instrumented tests through the [CustomTestRunner].
 */
open class MainTestApplication : Application() {

    override fun onCreate() {
        // ThreeTenBP for times and dates, called before super to be available for objects
//        AndroidThreeTen.init(this)
        Timber.plant(Timber.DebugTree())
        super.onCreate()
    }
}
