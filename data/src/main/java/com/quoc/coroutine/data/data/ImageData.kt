package com.quoc.coroutine.data.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class ImageData(
    @PrimaryKey
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
)