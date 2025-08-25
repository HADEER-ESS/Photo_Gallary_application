package com.hadeer.photogalleryapplication.di

import android.content.Context
import androidx.room.Room
import com.hadeer.data.remote.AppDataBase
import com.hadeer.data.repoimpl.PhotoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context : Context):AppDataBase{
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "photo_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providePhotoDao(database : AppDataBase):PhotoDao{
        return database.photoDao()
    }
}