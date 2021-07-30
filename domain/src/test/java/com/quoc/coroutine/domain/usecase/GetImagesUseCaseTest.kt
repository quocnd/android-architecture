package com.quoc.coroutine.domain.usecase

import com.quoc.coroutine.domain.lib.Resource
import com.quoc.coroutine.domain.model.ImageModel
import com.quoc.coroutine.domain.param.LoadType
import com.quoc.coroutine.domain.repository.ImageRepository
import com.quoc.coroutine.domain.util.TestUtils
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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
class GetImagesUseCaseTest : BaseUseCaseTest() {
    @Mock
    lateinit var repository: ImageRepository
    private lateinit var useCase: GetImagesUseCase

    @Before
    fun setUp() {
        useCase = GetImagesUseCase(repository, dispatcher)
    }

    @Test
    fun getImageImages() = runBlockingTest {
        val type = LoadType.REFRESH
        val images = TestUtils.getImages(3)
        val flow = flow {
            emit(Resource.Success(images))
        }
        Mockito.`when`(repository.getImages(type)).thenReturn(flow)
        val result = useCase.invoke(type).first() as Resource.Success
        assertEquals(images, result.data)
    }
}