package com.quoc.coroutine.domain.model

import com.quoc.coroutine.data.db.entity.ImageEntity

data class ImageModel(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String? = null,
    val downloadUrl: String? = null
) {
    companion object {
        fun createEmpty() = ImageModel("", "", -1, -1, "", "")
    }

    fun getThumbnailUrl(baseUrl: String) = "$baseUrl/id/$id/100/100.jpg"
}

fun ImageEntity.toModel(): ImageModel {
    return ImageModel(
        id, author, width, height, url, downloadUrl
    )
}
