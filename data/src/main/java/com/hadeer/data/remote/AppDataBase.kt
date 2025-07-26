package com.hadeer.data.remote

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hadeer.data.repoimpl.PhotoDao
import com.hadeer.domain.entity.Photo

@Database(entities = [Photo::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao
}
private var photoDatabaseInstance : AppDataBase ?= null

        private val PHOTOS_DATABASE = "photo_db"



        @Synchronized
        fun getInstance(context : Context):AppDataBase{
            if(photoDatabaseInstance==null){
                photoDatabaseInstance = Room.databaseBuilder(
                    context.applicationContext, AppDataBase::class.java, PHOTOS_DATABASE
                ).build()
            }
            return photoDatabaseInstance!!
        }

