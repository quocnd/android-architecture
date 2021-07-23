package com.quoc.coroutine.domain.usecase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
abstract class BaseUseCaseTest {
    protected val dispatcher = TestCoroutineDispatcher()
}