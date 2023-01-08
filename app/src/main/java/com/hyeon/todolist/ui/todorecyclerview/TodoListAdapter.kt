package com.hyeon.todolist.ui.todorecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hyeon.todolist.data.Todo
import com.hyeon.todolist.databinding.TodolistItemBinding

class TodoListAdapter(var todoList : ArrayList<Todo>) : Adapter<TodoListAdapter.TodoListViewHoler> (){

    class TodoListViewHoler(val binding : TodolistItemBinding) : ViewHolder(binding.root) {

        fun bind(todo : Todo){

            binding.checkboxTodo.setOnCheckedChangeListener(null)
            binding.checkboxTodo.isChecked = todo.isDo

            binding.textViewTodo.text = todo.content

            binding.imageViewMore.setOnClickListener(null)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHoler {
        val binding = TodolistItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TodoListViewHoler(binding)
    }

    override fun onBindViewHolder(holder: TodoListViewHoler, position: Int) {
        holder.bind(todoList[position])
    }

    override fun getItemCount(): Int {
        return todoList.size
    }
}