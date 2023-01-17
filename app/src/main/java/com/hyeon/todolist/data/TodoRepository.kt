package com.hyeon.todolist.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.hyeon.todolist.Todo

class TodoRepository (application : Application){
    private var mTodoDatabase : TodoDatabase
    private var mTodoDao : TodoDao
    private var mTodoItems : LiveData<List<Todo>>
    private lateinit var mTodoList : LiveData<List<Todo>>
    private var mTodoTypeList : LiveData<List<String>>
    private lateinit var mTimes : LiveData<List<Todo>>

    /*초기화*/
    init {
        mTodoDatabase = TodoDatabase.getInstance(application)
        mTodoDao = mTodoDatabase.todoDao()
        mTodoItems = mTodoDao.getAll()
        mTodoTypeList = mTodoDao.getType()
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

    fun getType() : LiveData<List<String>>{
        return mTodoTypeList
    }

    fun getTodolist(type : String, time : String) : LiveData<List<Todo>>{
        Thread{
            mTodoList = mTodoDao.getTodolist(type,time)
        }.start()
        return mTodoList
    }
    fun byTime(time : String) : LiveData<List<Todo>>{
        Thread{
            mTimes = mTodoDao.byTime(time)
        }.start()
        return mTimes
    }
}