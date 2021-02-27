package com.example.todolist_project.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Main(
    @PrimaryKey(autoGenerate=true) var id: Long?,
    @ColumnInfo(name = "db_goal") var db_goal: String?,
    @ColumnInfo(name = "db_time") var db_time: String?,
    @ColumnInfo(name = "db_day") var db_day: String?

)
{

    constructor(): this(null,"", "","")

}
