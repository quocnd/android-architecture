package com.quoc.coroutine.util

import com.quoc.coroutine.domain.model.ImageModel

object TestUtils {

    fun getImage(id: String) = ImageModel(id, "John", 1, 1, "url", "download_url")

    fun getImages(size: Int): List<ImageModel> {
        val images = ArrayList<ImageModel>()
        for (i in 0 until size) {
            images.add(getImage(i.toString()))
        }
        return images
    }
}