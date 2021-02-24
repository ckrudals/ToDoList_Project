package com.example.todolist_project.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Main::class], version = 2)
abstract class MainDatabase : RoomDatabase() {
    abstract fun mainDao(): MainDao


    }
