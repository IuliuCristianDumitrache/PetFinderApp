package com.dumitrachecristian.petapp.data

import android.content.Context
import androidx.room.Room

object DatabaseHelper {
    private lateinit var database: AppDatabase

    private const val DB_NAME = "PET_DB"

    fun initDB(context: Context) {
        database = Room.databaseBuilder(
            context,
            AppDatabase::class.java, DB_NAME
        )
            .build()
    }
}