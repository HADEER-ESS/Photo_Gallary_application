package com.hadeer.photogalleryapplication.ui

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.hadeer.data.repoimpl.PhotoDao
import com.hadeer.domain.NetworkResponse
import com.hadeer.domain.entity.Photo
import com.hadeer.domain.usecase.PhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.asLiveData
import com.hadeer.data.remote.getInstance
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val photoUseCase: PhotoUseCase,
    val context :Application
): ViewModel() {
    private var currentState = PhotosState()
    private val _photosIntent = MutableSharedFlow<PhotosIntent>()
    val photosIntent = _photosIntent.asSharedFlow()


    fun getPhotosData(){
        viewModelScope.launch {
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
                        photosData = response.body,
                    )
                    _photosIntent.emit(
                        PhotosIntent.Success(currentState)
                    )
                    response.body.forEach {item->
                        val photo = Photo(
                            id = item.id!!,
                            url = downloadAndSaveImage(item.url)!!,
                            alt = item.alt!!
                        )
                        insertPhotos(photo)
                    }
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
                        PhotosIntent.NetworkFailed(currentState)
                    )
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun insertPhotos(photo : Photo){
        GlobalScope.launch {
            getInstance(context).photoDao().cachePhoto(photo)
        }
    }

     suspend fun downloadAndSaveImage(url: String?): String? = withContext(Dispatchers.IO){
        val bitmap = Glide.with(context)
            .asBitmap()
            .load(url)
            .submit()
            .get()

        val fileName = "img_${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, fileName)

        FileOutputStream(file).use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }

        return@withContext file.absolutePath // Save this in Room
    }
}