package com.hadeer.photogalleryapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.hadeer.data.remote.AppDataBase
import com.hadeer.data.repoimpl.PhotoDao
import com.hadeer.domain.entity.Photo
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class PhotosDatabaseTest {
    private lateinit var photosDao : PhotoDao
    private lateinit var db : AppDataBase

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule() // Add this for LiveData testing

    @Before
    fun createDb(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        //in-memory means=>
        //the database is not actually saved on the file system,
        //and will be deleted once the TESTS have been run
        //when create an in-memory database, we use (allowMainThreadQueries) method

        //by default you will get error when you try to run QUERY on MAIN thread,
        //this METHOD allow us to run out test in the main thread.
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDataBase::class.java
            ).allowMainThreadQueries().build()

        photosDao = db.photoDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertPhotoAndGetData() = runBlocking{ // Use runTest for coroutines
        val photo = Photo(
            id = 1,
            url = "https://test.com/photo.jpg",
            alt = "Test Photo"
        )
        //Act
        photosDao.cachePhoto(photo)

        // Assert - Handle LiveData properly
        val photoData = photosDao.getAllPhotos().getOrAwaitValue()

        // Better assertions
        assertFalse(photoData.isEmpty())
        assertEquals(1, photoData.size)
        assertEquals("Test Photo", photoData[0].alt)
        assertEquals("https://test.com/photo.jpg", photoData[0].url)

        // If ID is auto-generated, check it's valid (not -1, 0, or null)
        assertTrue(photoData[0].id > 0) // or whatever your valid ID range is
    }
//    suspend fun insertPhotoAndGetData(){
//        val photo = Photo()
//        photosDao.cachePhoto(photo)
//        val photosData = photosDao.getAllPhotos()
//        assertEquals(photosData.value?.get(0)?.id , -1)
//    }

    fun <T> LiveData<T>.getOrAwaitValue(
        time : Long = 2,
        timeUnit : TimeUnit = TimeUnit.SECONDS
    ):T{
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(value: T) {
                data = value
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }
        this.observeForever(observer)

        try {
            if (!latch.await(time, timeUnit)) {
                throw TimeoutException("LiveData value was never set.")
            }
        } finally {
            this.removeObserver(observer)
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    }
}