package com.hyeon.todolist.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.hyeon.todolist.Todo

class TodoRepository (application : Application){
    private lateinit var mTodoDatabase : TodoDatabase
    private lateinit var mTodoDao : TodoDao
    private lateinit var mTodoItems : LiveData<List<Todo>>
    private lateinit var mTodoList : LiveData<List<Todo>>
    private lateinit var mTodoTypeList : LiveData<List<Todo>>

    /*초기화*/
    init {
        mTodoDatabase = TodoDatabase.getInstance(application)
        mTodoDao = mTodoDatabase.todoDao()
        mTodoItems = mTodoDao.getAll()
    }
    fun getAll() : LiveData<List<Todo>>{
        return mTodoItems
    }
    fun insert(dto : Todo){
        Thread{
            mTodoDao.insert(dto)
        }.start()
    }
    fun delete(dto : Todo){
        Thread{
            mTodoDao.delete(dto)
        }.start()
    }
    fun update(dto : Todo){
        Thread{
            mTodoDao.update(dto)
        }.start()
    }
    fun getType(id : Int) : LiveData<List<Todo>>{
        Thread{
            mTodoTypeList = mTodoDao.getType(id)
        }.start()
        return mTodoTypeList
    }
    fun getTodolist(type : String, time : String) : LiveData<List<Todo>>{
        Thread{
            mTodoList = mTodoDao.getTodolist(type,time)
        }.start()
        return mTodoList
    }
}