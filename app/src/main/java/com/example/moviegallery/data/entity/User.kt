package com.example.moviegallery.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "user_id") var userId: String,
    @ColumnInfo(name = "user_name") var userName: String,
    @ColumnInfo(name = "user_password") var userPassword: String,
    @ColumnInfo(name = "user_created_date") var userCreatedDate: String,
    @ColumnInfo(name = "user_login_date") var userLoginDate: String,
    @ColumnInfo(name = "user_active") var userActive: String = "A"
)