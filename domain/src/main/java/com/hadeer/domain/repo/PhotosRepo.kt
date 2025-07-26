package com.hadeer.domain.repo

import androidx.lifecycle.LiveData
import com.hadeer.domain.NetworkResponse
import com.hadeer.domain.entity.Photo
import com.hadeer.domain.entity.PhotoModel

interface PhotosRepo {
    suspend fun getAllPhotos():NetworkResponse<List<PhotoModel>>
}