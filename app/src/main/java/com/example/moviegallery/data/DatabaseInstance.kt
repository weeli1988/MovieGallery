package com.example.moviegallery.data

import android.content.Context
import com.example.moviegallery.utils.ApikeyInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DatabaseInstance {

    private const val baseUrl = "https://www.omdbapi.com/"
    private const val apiKey = "6fc87060"

    fun getInstance(context: Context) : Retrofit {

        val gson = GsonBuilder().setLenient().create()

        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

}