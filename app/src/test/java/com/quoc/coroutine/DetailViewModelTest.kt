package com.quoc.coroutine

import com.quoc.coroutine.domain.entity.ImageEntity
import com.quoc.coroutine.domain.usecase.GetImageDetailUseCase
import com.quoc.coroutine.ui.detail.DetailViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {
    @Mock
    lateinit var getImageDetailUseCase: GetImageDetailUseCase
    private lateinit var viewModel: DetailViewModel

    @Before
    fun init() {
        viewModel = DetailViewModel(getImageDetailUseCase)
    }

    @Test
    fun getImageDetailTest() = runBlocking {
        val imageEntity = ImageEntity("1", "John", 1, 1, "url", "download_url")
        val result = flow {
            emit(imageEntity)
        }
        Mockito.`when`(getImageDetailUseCase.execute(imageEntity.id)).thenReturn(result)
        viewModel.getDetail(imageEntity.id)
        val data = viewModel.imageDetail
        assertEquals(imageEntity, data.value)
    }


}