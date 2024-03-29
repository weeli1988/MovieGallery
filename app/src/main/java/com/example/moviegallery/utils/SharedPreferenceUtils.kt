package com.example.moviegallery.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.moviegallery.R

object SharedPreferenceUtils {

    private lateinit var sharedPref : SharedPreferences

    fun getSharedPref() : SharedPreferences {
        return sharedPref
    }

    fun init(context: Context) {

        // create shared preference
        sharedPref = context.getSharedPreferences(context.getString(R.string.sp_application_settings), AppCompatActivity.MODE_PRIVATE)
    }
}