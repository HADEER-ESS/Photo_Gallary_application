package com.hadeer.photogalleryapplication.di

import com.hadeer.photogalleryapplication.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization" , BuildConfig.AUTH_TOKEN)
            .build()

        return chain.proceed(request)
    }
}