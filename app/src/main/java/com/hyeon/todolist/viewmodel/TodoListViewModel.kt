package com.hyeon.todolist.viewmodel

import androidx.lifecycle.ViewModel
import com.hyeon.todolist.data.Todo

class TodoListViewModel : ViewModel() {
    var todoList : ArrayList<ArrayList<Todo>> = arrayListOf(
        arrayListOf(Todo("운동","푸쉬업 100개",false),Todo("운동","풀업 100개",true),Todo("운동","런닝10분",false)),
        arrayListOf(Todo("개 산책","1시간",false)),
        arrayListOf(Todo("공부","Flutter UI 구성",false),Todo("공부","Android",true),Todo("공부","Toeic",false)),
    )
}