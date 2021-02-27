package com.example.todolist_project.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Main::class], version = 6)
abstract class MainDatabase : RoomDatabase() {
    abstract fun mainDao(): MainDao


    companion object {
        private var INSTANCE: MainDatabase? = null

        fun getInstance(context: Context): MainDatabase? {
            if (INSTANCE == null) {
                synchronized(MainDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MainDatabase::class.java, "Main"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }


    }
