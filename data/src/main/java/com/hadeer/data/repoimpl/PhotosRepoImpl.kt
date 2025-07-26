package com.hadeer.data.repoimpl

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.hadeer.data.CheckInternetConnectivity
import com.hadeer.data.remote.ApiService
import com.hadeer.domain.NetworkResponse
import com.hadeer.domain.entity.PhotoModel
import com.hadeer.domain.entity.toGetData
import com.hadeer.domain.repo.PhotosRepo
import java.io.IOException

class PhotosRepoImpl (
     val apiService: ApiService,
     val context : Context
): PhotosRepo {
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun getAllPhotos() : NetworkResponse<List<PhotoModel>> {
        if(CheckInternetConnectivity.isInternetEnable(context)){
            val response = apiService.getImages(2,40)
            if(response.isSuccessful){
                return NetworkResponse.Success(
                    response.body()?.photos?.map {
                        it!!.toGetData()
                    } ?: listOf()
                )
            }
            else if(response.code() == 401){
                return NetworkResponse.ApiError("This account isn't Autherized, or you exceed the request limit", response.code())
            }
            else{
                return NetworkResponse.UnknownError(
                    Throwable("Something went wrong please try again later")
                )
            }
        }
        else{
            return NetworkResponse.NetworkError(
                IOException("No Internet connection")
            )
        }

    }

}