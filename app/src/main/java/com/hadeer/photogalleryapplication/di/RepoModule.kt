package com.hadeer.photogalleryapplication.di

import android.content.Context
import com.hadeer.data.remote.ApiService
import com.hadeer.data.repoimpl.PhotosRepoImpl
import com.hadeer.domain.repo.PhotosRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    fun providePhotosRepo(apiService: ApiService , @ApplicationContext context: Context):PhotosRepo = PhotosRepoImpl(
        apiService, context
    )
}