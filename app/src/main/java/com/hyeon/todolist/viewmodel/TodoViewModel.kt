package com.hyeon.todolist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hyeon.todolist.Todo
import com.hyeon.todolist.data.TodoRepository

class TodoViewModel(application : Application) : AndroidViewModel(application) {
    private var mTodoRepository : TodoRepository
    private lateinit var mTodolist : LiveData<List<Todo>>
    private lateinit var mTodoTypeList : LiveData<List<Todo>>

    init{
        mTodoRepository = TodoRepository(application)
    }
    fun getTodolist(type : String, time : String) : LiveData<List<Todo>> {
        Thread{
            mTodolist = mTodoRepository.getTodolist(type,time)
        }.start()
        return mTodolist
    }
    fun getType(id : Int) : LiveData<List<Todo>> {
        Thread{
            mTodoTypeList = mTodoRepository.getType(id)
        }.start()
        return mTodoTypeList
    }

    fun insert(todo : Todo){
        mTodoRepository.insert(todo)
    }

    fun delete(todo : Todo){
        mTodoRepository.delete(todo)
    }

    fun update(todo : Todo){
        mTodoRepository.update(todo)
    }
}