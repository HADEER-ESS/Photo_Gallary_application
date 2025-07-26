package com.hadeer.domain.usecase

import androidx.lifecycle.LiveData
import com.hadeer.domain.NetworkResponse
import com.hadeer.domain.entity.Photo
import com.hadeer.domain.entity.PhotoModel
import com.hadeer.domain.repo.PhotosRepo

class PhotoUseCase(private val photoRepo : PhotosRepo){
    suspend fun getPhotosData():NetworkResponse<List<PhotoModel>> = photoRepo.getAllPhotos()
}