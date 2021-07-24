package com.quoc.coroutine.domain.usecase

import com.quoc.coroutine.domain.lib.Resource
import com.quoc.coroutine.domain.lib.data
import com.quoc.coroutine.domain.repository.ImageRepository
import com.quoc.coroutine.domain.util.TestUtils
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetImageDetailUseCaseTest: BaseUseCaseTest() {
    @Mock
    lateinit var repository: ImageRepository
    private lateinit var useCase: GetImageDetailUseCase

    @Before
    fun setUp() {
        useCase = GetImageDetailUseCase(repository, dispatcher)
    }

    @Test
    fun getImageDetail() = runBlocking {
        val id = "1"
        val imageEntity = TestUtils.getImage(id)
        val flow = flow {
            emit(Resource.Success(imageEntity))
        }
        Mockito.`when`(repository.getImageDetail(id)).thenReturn(flow)
        val result = useCase.invoke(id).first()
        assertEquals(imageEntity, result.data)
    }
}