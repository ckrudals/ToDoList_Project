package com.example.todolist_project

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.todolist_project.Room.Main
import com.example.todolist_project.Room.MainDatabase

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val db = Room.databaseBuilder(
        application,
        MainDatabase::class.java, "MainDatabase"
    ).build()

    fun getAll(): LiveData<List<Main>> {
        return db.mainDao().getAll()
    }
    suspend fun insert(main:Main){
        db.mainDao().insert(main)
    }

}