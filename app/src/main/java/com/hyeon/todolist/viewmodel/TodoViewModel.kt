package com.hyeon.todolist.viewmodel

import android.app.Application
import android.content.ClipData
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hyeon.todolist.data.Todo
import com.hyeon.todolist.data.TodoRepository
import com.prolificinteractive.materialcalendarview.CalendarDay

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
    fun getTodo(type:String,content: String) : LiveData<Todo>{
        return mTodoRepository.getTodo(type,content,selectedDayFormat)
    }

    fun insert(todo : Todo){
        mTodoRepository.insert(todo)
    }

    fun insert(type : String, content : String, isChecked : Boolean){
        mTodoRepository.insert(Todo(type,content,selectedDayFormat,isChecked))
    }

    fun delete(todo : Todo){
        mTodoRepository.delete(todo)
    }

    fun update(todo : Todo){
        mTodoRepository.update(todo)
    }


    var selectedDay:CalendarDay = CalendarDay.today()

    var selectedDayFormat:String = selectedDay.toString().apply {
        substring(12,length-1)
    }
}
//
//class TodoViewModelFactory(private val application: Application) : ViewModelProvider.Factory{
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if(modelClass.isAssignableFrom(TodoViewModel::class.java)){
//            @Suppress("UNCHECKED_CAST")
//            return TodoViewModel(application) as T
//        }
//        throw IllegalAccessException("Unknown ViewModel Class")
//    }
//}