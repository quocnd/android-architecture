package com.quoc.coroutine.data.api.response

import com.quoc.coroutine.data.db.entity.ImageEntity
import com.squareup.moshi.Json

data class ImageResponse(
    @Json(name = "id")
    val id: String,
    @Json(name = "author")
    val author: String,
    @Json(name = "width")
    val width: Int,
    @Json(name = "height")
    val height: Int,
    @Json(name = "url")
    val url: String? = null,
    @Json(name = "download_url")
    val downloadUrl: String? = null
) {
    fun toEntity() = ImageEntity(id, author, width, height, url, downloadUrl)
}