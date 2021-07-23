package com.quoc.coroutine.ui.detail

import com.quoc.coroutine.domain.lib.Result
import com.quoc.coroutine.domain.repository.ImageRepository
import com.quoc.coroutine.domain.usecase.GetImageDetailUseCase
import com.quoc.coroutine.ui.BaseViewModelTest
import com.quoc.coroutine.ui.detail.DetailViewModel
import com.quoc.coroutine.util.TestUtils
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest: BaseViewModelTest() {
    @Mock
    lateinit var repository: ImageRepository
    lateinit var getImageDetailUseCase: GetImageDetailUseCase
    private lateinit var viewModel: DetailViewModel

    @Before
    fun init() {
        getImageDetailUseCase = GetImageDetailUseCase(repository, dispatcher)
        viewModel = DetailViewModel(getImageDetailUseCase)
    }

    @Test
    fun getImageDetailTest() = runBlockingTest {
        val imageEntity = TestUtils.getImage("1")
        val result = flow {
            emit(Result.Success(imageEntity))
        }
        Mockito.`when`(getImageDetailUseCase.invoke(imageEntity.id)).thenReturn(result)
        viewModel.getDetail(imageEntity.id)
        val data = viewModel.imageDetail
        assertEquals(imageEntity, data.value)
    }


}