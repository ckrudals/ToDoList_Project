package com.example.todolist_project.Room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MainDao {
    @Query("SELECT * FROM Main")
    fun getAll(): LiveData<List<Main>>
    
    @Insert
    fun insert(main:Main)
    
    @Update
    fun update(main:Main)
    
    @Delete
    fun delete(main:Main)

}