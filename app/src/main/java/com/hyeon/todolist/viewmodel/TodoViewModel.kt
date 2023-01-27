package com.hyeon.todolist.viewmodel

import android.app.Application
import android.content.ClipData
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyeon.todolist.Todo
import com.hyeon.todolist.data.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(application : Application) : AndroidViewModel(application) {
    private var mTodoRepository : TodoRepository
    private var mTodoTypeList : LiveData<List<String>>
    private var mTodos : LiveData<List<Todo>>

    init{
        mTodoRepository = TodoRepository(application)
        mTodos = mTodoRepository.getAll()
        mTodoTypeList = mTodoRepository.getType()
    }
    fun byTime(time : String) : LiveData<List<Todo>>{
        return mTodoRepository.byTime(time)
    }
    fun getTodolist(type : String, time : String) : LiveData<List<Todo>> {
       return mTodoRepository.getTodolist(type,time)
    }
    fun getType() : LiveData<List<String>> {
        return mTodoTypeList
    }
    fun getAll() : LiveData<List<Todo>> {
        return mTodos
    }

    fun insert(todo : Todo){
        viewModelScope.launch(Dispatchers.IO){
            mTodoRepository.insert(todo)
        }
    }

    fun delete(todo : Todo){
        mTodoRepository.delete(todo)
    }

    fun update(todo : Todo){
        mTodoRepository.update(todo)
    }
}