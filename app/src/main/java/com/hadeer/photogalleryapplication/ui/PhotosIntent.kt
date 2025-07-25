package com.hadeer.photogalleryapplication.ui

sealed class PhotosIntent {
    data class Idle(val state: PhotosState): PhotosIntent()
    data class Failed(val state: PhotosState):PhotosIntent()
    data class Success(val state: PhotosState): PhotosIntent()
}