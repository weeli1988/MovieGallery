package com.example.moviegallery.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviegallery.data.entity.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie")
    fun getMovies() : List<Movie>

    @Insert(onConflict = OnConflictStrategy.IGNORE) // we can use conflict strategy like OnConflictStrategy.IGNORE
    suspend fun insertMovies(movies: List<Movie>) : List<Long>

    @Query("SELECT * FROM Movie WHERE movie_name LIKE '%' || :movieName || '%'")
    suspend fun getMoviesByName(movieName: String) : List<Movie>

    @Query("SELECT * FROM Movie WHERE movie_name LIKE '%' || :movieName || '%'")
    fun getMoviesByNameOnSync(movieName: String) : List<Movie>

    @Query("SELECT * FROM Movie WHERE movie_type = :movieType")
    suspend fun getMoviesByType(movieType: String) : List<Movie>

    @Query("SELECT * FROM Movie WHERE movie_year = :movieYear")
    suspend fun getMoviesByYear(movieYear: String) : List<Movie>

    @Delete
    suspend fun deleteMovie(movie: Movie)
}