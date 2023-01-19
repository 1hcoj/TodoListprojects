package com.hyeon.todolist.data

import androidx.room.*

@Entity(tableName = "todoTable")
data class Todo(
    @ColumnInfo(name = "type") val type : String,
    @ColumnInfo(name = "content") var content : String,
    @ColumnInfo(name = "time") var time : String,
    @ColumnInfo(name = "isChecked") var isChecked : Boolean
){
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}
