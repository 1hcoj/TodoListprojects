package com.hyeon.todolist.viewmodel

import androidx.lifecycle.ViewModel

class TodoTypeViewModel : ViewModel() {
    val todoTypeNameList : ArrayList<String> = arrayListOf(
        "운동","공부","알바","개 산책"
    )
}