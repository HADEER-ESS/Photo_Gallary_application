package com.hadeer.domain.repo

import com.hadeer.domain.NetworkResponse
import com.hadeer.domain.entity.PhotoModel

interface PhotosRepo {
    suspend fun getAllPhotos():NetworkResponse<List<PhotoModel>>
}