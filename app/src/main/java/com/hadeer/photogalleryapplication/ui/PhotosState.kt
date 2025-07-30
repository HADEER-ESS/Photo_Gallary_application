package com.hadeer.photogalleryapplication.ui

import com.hadeer.domain.entity.Photo
import com.hadeer.domain.entity.PhotoModel

data class PhotosState(
    val isLoading : Boolean = false,
    val apiError : String = "",
    val isSuccess : Boolean = false,
    val photosData : List<PhotoModel> = emptyList(),
    val photosFromStorage : List<Photo> = emptyList()
)
