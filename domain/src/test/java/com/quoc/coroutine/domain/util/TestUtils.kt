package com.quoc.coroutine.domain.util

import android.media.Image
import com.quoc.coroutine.domain.entity.ImageEntity

object TestUtils {

    fun getImage(id: String) = ImageEntity(id, "John", 1, 1, "url", "download_url")

    fun getImages(size: Int): List<ImageEntity> {
        val images = ArrayList<ImageEntity>()
        for (i in 0 until size) {
            images.add(getImage(i.toString()))
        }
        return images
    }
}