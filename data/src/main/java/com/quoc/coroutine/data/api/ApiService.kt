package com.quoc.coroutine.data.api

import com.quoc.coroutine.data.data.ImageData
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/v2/list")
    suspend fun getNextImages(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<ImageData>

    @GET("/id/{id}/info")
    suspend fun detail(@Path("id") id: String): ImageData
}