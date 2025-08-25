package com.hadeer.data.remote

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hadeer.data.repoimpl.PhotoDao
import com.hadeer.domain.entity.Photo

/*
Why you need to keep the Database class:

Room requires it - This defines your database schema
Entities definition - entities = [Photo::class]
DAO provision - abstract fun photoDao(): PhotoDao
Database version - version = 1
*/



@Database(entities = [Photo::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
//    abstract val photoDao: PhotoDao
    abstract fun photoDao() : PhotoDao


//    DELETE only the companion object (the manual singleton code):
    // Hilt replaces this functionality
//    companion object{
//        private val PHOTOS_DATABASE = "photo_db"
//        @Volatile
//        private var INSTANCE : AppDataBase? = null
//
//        fun getInstance(context : Context): AppDataBase{
//            synchronized(this){
//                var instance = INSTANCE
//
//                if(instance == null){
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        AppDataBase::class.java,
//                        PHOTOS_DATABASE
//                    ).fallbackToDestructiveMigration()
//                        .build()
//                    INSTANCE = instance
//                }
//
//                return instance
//            }
//        }
//    }
}

