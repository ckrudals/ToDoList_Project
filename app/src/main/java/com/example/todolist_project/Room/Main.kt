package com.example.todolist_project.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Main(

    val db_goal: String,
    val db_time: String?,
    val db_day:String?
) {

    @PrimaryKey(autoGenerate=true) var id: Int=0
}
