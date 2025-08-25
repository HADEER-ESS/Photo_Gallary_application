package com.hadeer.data.repoimpl

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hadeer.domain.entity.Photo

@Dao
interface PhotoDao {
    @Query("SELECT * FROM photos")
    fun getAllPhotos() : LiveData<List<Photo>>

    @Query("SELECT * FROM photos")
    suspend fun getAllPhotosAsync():List<Photo>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun cachePhoto(photo: Photo)
}