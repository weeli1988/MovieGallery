package com.example.moviegallery.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviegallery.data.dao.MovieDao
import com.example.moviegallery.data.entity.Movie
import com.example.moviegallery.data.entity.User

@Database(
    entities = [User::class, Movie::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao() : MovieDao

    companion object {

        @Volatile
        private var database: AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase {

            return database ?: synchronized(this) {
                val database = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java
                    , "App_Database").allowMainThreadQueries().build()
                this.database = database
                database
            }
        }
    }
}