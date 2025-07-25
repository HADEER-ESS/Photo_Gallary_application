package com.hadeer.photogalleryapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadeer.domain.NetworkResponse
import com.hadeer.domain.usecase.PhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val photoUseCase: PhotoUseCase
): ViewModel() {
    private var currentState = PhotosState()
    private val _photosIntent = MutableSharedFlow<PhotosIntent>()
    val photosIntent = _photosIntent.asSharedFlow()

    fun getPhotosData(){
        viewModelScope.launch {
            println("enter view model function")
            currentState = currentState.copy(
                isLoading = true
            )
            _photosIntent.emit(
                PhotosIntent.Idle(currentState)
            )

            val response = photoUseCase.getPhotosData()
            when(response){
                is NetworkResponse.Success ->{
                    currentState = currentState.copy(
                        isLoading = false,
                        isSuccess = true,
                        photosData = response.body
                    )
                    _photosIntent.emit(
                        PhotosIntent.Success(currentState)
                    )
                }
                is NetworkResponse.ApiError -> {
                    currentState = currentState.copy(
                        isLoading = false,
                        isSuccess = false,
                        apiError = response.body
                    )
                    _photosIntent.emit(
                        PhotosIntent.Failed(currentState)
                    )
                }
                is NetworkResponse.UnknownError ->{
                    currentState = currentState.copy(
                        isLoading = false,
                        isSuccess = false,
                        apiError = response.error.message!!
                    )
                    _photosIntent.emit(
                        PhotosIntent.Failed(currentState)
                    )
                }
                is NetworkResponse.NetworkError ->{
                    currentState = currentState.copy(
                        isLoading = false,
                        isSuccess = false,
                        apiError = response.error.message!!
                    )
                    _photosIntent.emit(
                        PhotosIntent.Failed(currentState)
                    )
                }
            }
        }
    }
}