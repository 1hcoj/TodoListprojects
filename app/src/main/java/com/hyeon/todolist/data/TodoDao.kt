package com.hyeon.todolist.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hyeon.todolist.Todo

@Dao
interface TodoDao{
    @Query("SELECT * FROM todoTable WHERE type = (:type) AND time = (:time)")
    fun getTodolist(type : String, time : String) : LiveData<List<Todo>>

    @Query("SELECT * FROM todoTable WHERE id = (:id)")
    fun getType(id : Int) : LiveData<List<Todo>>

    @Query("SELECT * FROM todoTable")
    fun getAll() : LiveData<List<Todo>>

    @Insert
    fun insert(dto : Todo)

    @Update
    fun update(dto : Todo)

    @Delete
    fun delete(dto : Todo)
}
