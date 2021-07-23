package com.quoc.coroutine.ui

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
open class BaseViewModelTest {
    val dispatcher = TestCoroutineDispatcher()
}