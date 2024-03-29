package com.example.moviegallery.data

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("path/to/api")
    fun doAuthentication(
        @Field("user_id") userId: String,
        @Field("user_password") userPassword: String
    ) : Call<JsonObject>

    @GET("?type=movie")
    fun getMovies(
        @Query(value = "apiKey") apiKey: String,
        @Query(value = "s") s: String
    ): Call<JsonObject>

    @GET("?")
    fun getMovieDetail(
        @Query(value = "apiKey") apiKey: String,
        @Query(value = "i") s: String
    ): Call<JsonObject>
}