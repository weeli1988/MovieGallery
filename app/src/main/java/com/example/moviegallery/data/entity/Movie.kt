package com.example.moviegallery.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["movie_name"], unique = true)])
data class Movie (
    @ColumnInfo(name = "movie_name",  collate = 3) var movieName: String,
    @ColumnInfo(name = "movie_type") var movieType: String,
    @ColumnInfo(name = "movie_year") var movieYear: String,
    @ColumnInfo(name = "movie_imdb") var movieImdb: String,
    @ColumnInfo(name = "movie_poster") var moviePoster: String,
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}