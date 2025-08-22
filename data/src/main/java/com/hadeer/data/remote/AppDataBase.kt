package com.hadeer.data.remote

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hadeer.data.repoimpl.PhotoDao
import com.hadeer.domain.entity.Photo

@Database(entities = [Photo::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract val photoDao: PhotoDao

    companion object{
        private val PHOTOS_DATABASE = "photo_db"
        @Volatile
        private var INSTANCE : AppDataBase? = null

        fun getInstance(context : Context): AppDataBase{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java,
                        PHOTOS_DATABASE
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}

