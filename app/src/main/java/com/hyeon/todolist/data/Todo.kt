package com.hyeon.todolist

import androidx.room.*

@Entity(tableName = "todoTable")
data class Todo(
    @ColumnInfo(name = "type") val type : String,
    @ColumnInfo(name = "content") val content : String,
    @ColumnInfo(name = "time") val time : String,
    @ColumnInfo(name = "isChecked") val isChecked : Boolean
){
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}
