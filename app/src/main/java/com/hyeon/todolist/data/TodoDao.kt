package com.hyeon.todolist.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import com.hyeon.todolist.data.Todo

@Dao
interface TodoDao{
    @Query("SELECT * FROM todoTable WHERE type = (:type) AND time = (:time)")
    fun getTodolist(type : String, time : String) : LiveData<List<Todo>>

    @Query("SELECT * FROM todoTable WHERE time = (:time)")
    fun byTime(time : String) : LiveData<List<Todo>>

    @Query("SELECT * FROM todoTable WHERE content = (:key)")
    fun getType(key : String) : LiveData<List<Todo>>

    @Query("SELECT * FROM todoTable")
    fun getAll() : LiveData<List<Todo>>

    @Query("SELECT * FROM todoTable WHERE type=(:type) AND content=(:content) AND time=(:time)")
    fun getTodo(type: String,content : String,time:String) : LiveData<Todo>

    @Insert
    fun insert(dto : Todo)

    @Update
    fun update(dto : Todo)

    @Delete
    fun delete(dto : Todo)
}