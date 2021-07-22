package com.quoc.coroutine.di

import com.quoc.coroutine.BuildConfig
import com.quoc.coroutine.data.api.ApiService
import com.quoc.coroutine.data.api.interceptor.BaseInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @LoggingInterceptorType
    @Provides
    fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            HttpLoggingInterceptor.Level.BODY
        }
    }

    @BaseInterceptorType
    @Provides
    fun provideBasicInterceptor(): Interceptor {
        return BaseInterceptor()
    }

    @Provides
    fun provideOkHttpClient(
        @LoggingInterceptorType httpLoggingInterceptor: Interceptor,
        @BaseInterceptorType baseInterceptor: Interceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
//        if (BuildConfig.DEBUG) {
        builder.addInterceptor(httpLoggingInterceptor)
//        }
        builder.addInterceptor(baseInterceptor)
        return builder.build()
    }

    @Provides
    fun provideRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .build()
    }

    @Provides
    fun apiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}