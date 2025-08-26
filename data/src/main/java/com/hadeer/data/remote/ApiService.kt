package com.hadeer.data.remote

import com.hadeer.domain.entity.Photo
import com.hadeer.domain.entity.PhotoResponse
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("curated")
    suspend fun getImages(@Query("page") pageNum:Int, @Query("per_page")perPage : Int):Response<PhotoResponse>
}