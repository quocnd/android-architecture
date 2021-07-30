package com.quoc.coroutine.data.db

import androidx.room.Dao
import androidx.room.Query
import com.quoc.coroutine.data.db.entity.ImageEntity

@Dao
interface ImageDao : BaseDao<ImageEntity> {

    @Query("SELECT * FROM ImageEntity")
    suspend fun getAllImages(): List<ImageEntity>

    @Query("SELECT * FROM ImageEntity WHERE id =:id")
    suspend fun getImage(id: String): ImageEntity

    @Query("DELETE FROM ImageEntity")
    suspend fun deleteAll()
}