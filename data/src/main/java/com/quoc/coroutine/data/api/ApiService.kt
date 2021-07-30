package com.quoc.coroutine.data.api

import com.quoc.coroutine.data.api.response.ImageResponse
import com.quoc.coroutine.data.db.entity.ImageEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/v2/list")
    suspend fun getNextImages(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<ImageResponse>

    @GET("/id/{id}/info")
    suspend fun detail(@Path("id") id: String): ImageResponse
}