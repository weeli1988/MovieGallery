package com.example.moviegallery.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviegallery.data.AppDatabase
import com.example.moviegallery.data.entity.Movie
import com.example.moviegallery.data.repo.MovieRepository
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val movieRepository = MovieRepository()
    private val movieDao = AppDatabase.getInstance(application).movieDao()

    private var movieDelete = MutableLiveData<Unit>()

    val movieDeleteLiveData: LiveData<Unit> get() = movieDelete

    private var moviesRetrieve = MutableLiveData<List<Movie>>()

    val moviesRetrieveLiveData: LiveData<List<Movie>> get() = moviesRetrieve

    private var moviesInsert = MutableLiveData<List<Long>>()

    val moviesInsertLiveData: LiveData<List<Long>> get() = moviesInsert

    private var movieDetail = MutableLiveData<JsonObject>()

    val movieDetailLiveData : LiveData<JsonObject> get() = movieDetail

    private var moviesJson = MutableLiveData<JsonObject>()

    val moviesJsonLiveData: LiveData<JsonObject> get() = moviesJson

    fun getMoviesFromApi(context: Context, movieName: String) {
        movieRepository.getMoviesByNameFromApi(moviesJson, context, movieName)
    }

    fun getMovieDetailFromApi(context: Context, movieImdb: String) {
        movieRepository.getMovieDetailByImdbFromApi(movieDetail, context, movieImdb)
    }

    fun insertMovies(movieList: List<Movie>) {
        viewModelScope.launch {
            moviesInsert.postValue(movieRepository.insertMovies(movieDao, movieList))
        }
    }

    fun deleteMovie(movie: Movie) {
        viewModelScope.launch {
            movieDelete.postValue(movieRepository.deleteMovie(movieDao, movie))
        }
    }

    // get all movies
    fun getMoviesFromSearchHistoryOnSync() {
        moviesRetrieve.postValue(movieRepository.getMovies(movieDao))
    }

    // get movies by name
    fun getMoviesByName(movieName: String) {
        viewModelScope.launch {
            moviesRetrieve.postValue(movieRepository.getMoviesByName(movieDao, movieName))
        }
    }

    fun getMoviesByNameOnSync(movieName: String) {
        moviesRetrieve.postValue(movieRepository.getMoviesByNameOnSync(movieDao, movieName))
    }

    // get movies by type
    fun getMoviesByType(movieType: String) {
        viewModelScope.launch {
            moviesRetrieve.postValue(movieRepository.getMoviesByType(movieDao, movieType))
        }
    }

    // get movies by year
    fun getMoviesByYear(movieYear: String) {
        viewModelScope.launch {
            moviesRetrieve.postValue(movieRepository.getMoviesByYear(movieDao, movieYear))
        }
    }
}