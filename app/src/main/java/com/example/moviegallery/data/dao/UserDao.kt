package com.example.moviegallery.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.moviegallery.data.entity.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun createUser(user: User) : Long

    @Delete
    suspend fun deleteUser(user: User) : Int

    @Query("SELECT * FROM User WHERE User.user_id = :userId AND User.user_password = :userPassword")
    suspend fun getUser(userId: String, userPassword: String): User

    @Update
    suspend fun updateUser(user: User)

    @Query("UPDATE User SET user_name = :newUserName AND user_password = :newUserPassword")
    suspend fun updateUser(newUserName: String, newUserPassword: String)
}