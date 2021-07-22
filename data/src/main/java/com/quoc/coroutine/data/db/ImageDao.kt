package com.quoc.coroutine.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.quoc.coroutine.data.data.ImageData

@Dao
interface ImageDao : BaseDao<ImageData> {

    @Query("SELECT * FROM ImageData")
    fun getAll(): PagingSource<Int, ImageData>

    @Query("SELECT * FROM ImageData")
    suspend fun getAllImages(): List<ImageData>

    @Query("DELETE FROM ImageData")
    suspend fun deleteAll()
}