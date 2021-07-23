package com.quoc.coroutine.data.db

import androidx.room.Dao
import androidx.room.Query
import com.quoc.coroutine.data.data.ImageData

@Dao
interface ImageDao : BaseDao<ImageData> {

    @Query("SELECT * FROM ImageData")
    suspend fun getAllImages(): List<ImageData>

    @Query("SELECT * FROM ImageData WHERE id =:id")
    suspend fun getImage(id: String): ImageData

    @Query("DELETE FROM ImageData")
    suspend fun deleteAll()
}