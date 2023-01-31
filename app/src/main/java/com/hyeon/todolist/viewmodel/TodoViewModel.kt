package com.hyeon.todolist.viewmodel

import android.app.Application
import android.content.ClipData
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hyeon.todolist.data.Todo
import com.hyeon.todolist.data.TodoRepository
import com.prolificinteractive.materialcalendarview.CalendarDay

class TodoViewModel(application : Application) : AndroidViewModel(application) {

    companion object{
        const val TYPE_KEY = "ThisIsTypeNotContent"
    }

    private var mTodoRepository : TodoRepository
    private var mTodoTypeList : LiveData<List<Todo>>
    private var mTodos : LiveData<List<Todo>>

    private val _todoList = MutableLiveData<MutableList<Todo>>()

    val todoList : LiveData<MutableList<Todo>>
        get()= _todoList


    init{
        mTodoRepository = TodoRepository(application)
        mTodos = mTodoRepository.getAll()

        mTodoTypeList = mTodoRepository.getType(TYPE_KEY)
        _todoList.value = mutableListOf()
    }

    fun byTime(time : String) : LiveData<List<Todo>>{
        return mTodoRepository.byTime(time)
    }

    fun getTodolist(type : String, time : String) : LiveData<List<Todo>> {
        return mTodoRepository.getTodolist(type,time)
    }

    fun getType() : LiveData<List<Todo>> {
        return mTodoTypeList
    }

    fun getAll() : LiveData<List<Todo>> {
        return mTodos
    }

    fun getTodo(type:String,content: String) : LiveData<Todo>{
        return mTodoRepository.getTodo(type,content,modifyDateFormat())
    }

    fun insert(todo : Todo){
        mTodoRepository.insert(todo)
    }

    fun insert(type : String, content : String, isChecked : Boolean){
        mTodoRepository.insert(Todo(type,content,modifyDateFormat(),isChecked))
    }

    fun delete(todo : Todo){
        mTodoRepository.delete(todo)
    }

    fun update(todo : Todo){
        mTodoRepository.update(todo)
    }

    var selectedDay:CalendarDay = CalendarDay.today()
    private fun modifyDateFormat() = selectedDay.toString().apply { substring(12,length-1) }

    /** 함수명 : updateDate
     *  매개변수 : calDate : CalendarDay ( 선택 날짜 )
     *  리턴형 : X
     *  함수 설명 :
     *      1. 날짜를 입력받아 viewModel 내부의 todoList 를 변경한다 .
     * */
    fun updateDate(calDate : CalendarDay){
        selectedDay = calDate
        updateTodoList()
    }

    private fun updateTodoList(){
        val tempTodo = mutableListOf<Todo>()

        mTodoTypeList.value?.let {
            for (i in it.indices){
                tempTodo.add(it[i])
                getTodolist(it[i].type,modifyDateFormat()).value?.let { todoList->
                    tempTodo.addAll(todoList)
                }
            }
        }

        _todoList.value!!.clear()
        _todoList.value!!.addAll(tempTodo)
    }
}


//class TodoViewModelFactory(private val application: Application) : ViewModelProvider.Factory{
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if(modelClass.isAssignableFrom(TodoViewModel::class.java)){
//            @Suppress("UNCHECKED_CAST")
//            return TodoViewModel(application) as T
//        }
//        throw IllegalAccessException("Unknown ViewModel Class")
//    }
//}