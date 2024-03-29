package com.example.moviegallery.data.repo

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.moviegallery.data.ApiService
import com.example.moviegallery.data.DatabaseInstance
import com.example.moviegallery.data.dao.MovieDao
import com.example.moviegallery.data.entity.Movie
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class MovieRepository {

    private val apiKey = "6fc87060"

    fun getMoviesByNameFromApi(result: MutableLiveData<JsonObject>, context: Context, movieName: String) {
        val instance = DatabaseInstance.getInstance(context)
        val apiService = instance.create(ApiService::class.java)

        apiService.getMovies(apiKey, movieName).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.body() != null) {
                    result.value = response.body()
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                result.value = null
            }
        })
    }

    fun getMovieDetailByImdbFromApi(result: MutableLiveData<JsonObject>, context: Context, movieImdb: String) {
        val instance = DatabaseInstance.getInstance(context)
        val apiService = instance.create(ApiService::class.java)

        apiService.getMovieDetail(apiKey, movieImdb).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.body() != null) {
                    result.value = response.body()
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                result.value = null
            }
        })
    }

    suspend fun insertMovies(movieDao: MovieDao, movieList: List<Movie>) : List<Long> {
        return movieDao.insertMovies(movieList)
    }

    suspend fun deleteMovie(movieDao: MovieDao, movie: Movie){
        return movieDao.deleteMovie(movie)
    }

    // get all movies
    fun getMovies(movieDao: MovieDao) : List<Movie> {
        return movieDao.getMovies()
    }

    // get movies by name
    suspend fun getMoviesByName(movieDao: MovieDao, movieName: String) : List<Movie> {
        return movieDao.getMoviesByName(movieName)
    }

    // get movies by name
    fun getMoviesByNameOnSync(movieDao: MovieDao, movieName: String) : List<Movie> {
        return movieDao.getMoviesByNameOnSync(movieName)
    }

    // get movies by type
    suspend fun getMoviesByType(movieDao: MovieDao, movieType: String) : List<Movie> {
        return movieDao.getMoviesByType(movieType)
    }

    // get movies by year
    suspend fun getMoviesByYear(movieDao: MovieDao, movieYear: String) : List<Movie> {
        return movieDao.getMoviesByYear(movieYear)
    }
}