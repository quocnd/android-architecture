package com.quoc.coroutine.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoggingInterceptorType

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseInterceptorType
