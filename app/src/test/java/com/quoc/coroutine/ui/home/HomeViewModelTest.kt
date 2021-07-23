package com.quoc.coroutine.ui.home

import com.quoc.coroutine.domain.lib.Result
import com.quoc.coroutine.domain.repository.ImageRepository
import com.quoc.coroutine.domain.usecase.GetImagesUseCase
import com.quoc.coroutine.ui.BaseViewModelTest
import com.quoc.coroutine.ui.home.HomeViewModel
import com.quoc.coroutine.util.TestUtils
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest: BaseViewModelTest() {

    @Mock
    lateinit var repository: ImageRepository
    lateinit var getImagesUseCase: GetImagesUseCase
    private lateinit var viewModel: HomeViewModel

    @Before
    fun init() {
        getImagesUseCase = GetImagesUseCase(repository, dispatcher)
        viewModel = HomeViewModel(getImagesUseCase)
    }

    @Test
    fun getImagesTest() = runBlockingTest {
        val images = TestUtils.getImages(2)
        val result = flow {
            emit(Result.Success(images))
        }
        Mockito.`when`(getImagesUseCase.invoke(Any())).thenReturn(result)
        viewModel.getImages()
        val data = viewModel.images
        assertEquals(images, data.value)
    }


}