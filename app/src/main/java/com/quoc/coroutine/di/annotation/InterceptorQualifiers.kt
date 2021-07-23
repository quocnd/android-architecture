package com.quoc.coroutine.di.annotation

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoggingInterceptorType

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseInterceptorType
