package com.example.moviegallery.utils

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class ApikeyInterceptor(private var apiKey: String) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request : Request = chain.request()
        val authRequest : Request = request.newBuilder().addHeader("apiKey", apiKey).build()
        return chain.proceed(authRequest)
    }
}