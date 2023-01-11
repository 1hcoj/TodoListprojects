package com.hyeon.todolist.ui.todorecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyeon.todolist.Todo
import com.hyeon.todolist.databinding.TodolistItemBinding

class TodoListAdapter(val checkedChangeListener: OnItemCheckedChangeListener) : RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder>(){
    private var todos : List<Todo> = listOf()
    inner class TodoListViewHolder(val binding : TodolistItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todo : Todo,
                 checkedChangeListener : OnItemCheckedChangeListener){
            binding.checkboxTodo.setOnCheckedChangeListener{_,isChecked ->
                checkedChangeListener.onItemCheckedChange(isChecked)
            }
            binding.checkboxTodo.isChecked = todo.isChecked

            binding.textViewTodo.text = todo.content

            binding.imageViewMore.setOnClickListener{

            }
        }
    }
    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : TodoListViewHolder{
        val binding = TodolistItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TodoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        val todo = todos[position]
        holder.bind(todo,checkedChangeListener)
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}

interface OnItemCheckedChangeListener{
    fun onItemCheckedChange(isCheck : Boolean)
}

