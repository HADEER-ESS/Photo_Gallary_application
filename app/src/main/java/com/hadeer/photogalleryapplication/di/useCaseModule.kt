package com.hadeer.photogalleryapplication.di

import com.hadeer.domain.repo.PhotosRepo
import com.hadeer.domain.usecase.PhotoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object useCaseModule {

    @Provides
    fun provideUseCase(photoRepo : PhotosRepo):PhotoUseCase {
        return PhotoUseCase(photoRepo)
    }
}