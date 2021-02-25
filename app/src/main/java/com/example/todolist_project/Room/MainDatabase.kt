package com.example.todolist_project.Room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Main::class], version = 4)
abstract class MainDatabase : RoomDatabase() {
    abstract fun mainDao(): MainDao


    }
