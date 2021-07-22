package com.quoc.coroutine.domain.usecase

import com.quoc.coroutine.domain.entity.ImageEntity
import com.quoc.coroutine.domain.repository.ImageRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetImageDetailUseCaseTest {
    @Mock
    lateinit var repository: ImageRepository
    private lateinit var useCase: GetImageDetailUseCase

    @Before
    fun setUp() {
        useCase = GetImageDetailUseCase(repository)
    }

    @Test
    fun getImageDetail() = runBlocking {
        val id = "1"
        val imageEntity = ImageEntity("1", "John", 1, 1, "url", "download_url")
        val flow = flow {
            emit(imageEntity)
        }
        Mockito.`when`(repository.getImageDetail(id)).thenReturn(flow)
        val data = useCase.execute(id).first()
        assertEquals(imageEntity, data)
    }
}