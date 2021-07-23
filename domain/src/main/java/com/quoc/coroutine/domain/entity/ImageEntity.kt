package com.quoc.coroutine.domain.entity

import com.quoc.coroutine.data.data.ImageData

data class ImageEntity(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String? = null,
    val downloadUrl: String? = null
) {
    companion object {
        fun createEmpty() = ImageEntity("", "", -1, -1, "", "")
    }

    fun getThumbnailUrl(baseUrl: String) = "$baseUrl/id/$id/100/100.jpg"
}

fun ImageData.toEntity(): ImageEntity {
    return ImageEntity(
        id, author, width, height, url, downloadUrl
    )
}
