package com.quoc.coroutine.data.api.interceptor

import dagger.hilt.EntryPoint
import okhttp3.Interceptor
import okhttp3.Response

class BaseInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}