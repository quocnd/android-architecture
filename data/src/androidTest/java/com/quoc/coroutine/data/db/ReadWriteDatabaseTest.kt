package com.quoc.coroutine.data.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.quoc.coroutine.data.data.ImageData
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReadWriteDatabaseTest {
    private lateinit var db: AppDatabase
    private lateinit var imageDao: ImageDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .build()
        imageDao = db.imageDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun testReadWrite() = runBlocking {
        val image1 = ImageData("1", "John", 1, 1, "url", "download_url")
        val image2 = ImageData("2", "Peter", 2, 2, "url2", "download_url_2")
        val inputList = listOf(image1, image2)

        imageDao.insertAll(inputList)
        val images = imageDao.getAllImages()
        assertEquals(inputList, images)

        imageDao.delete(image1)
        val remainList = imageDao.getAllImages()
        assertEquals(1, remainList.size)
        assertEquals(image2, remainList[0])

    }
}