package com.hyeon.todolist.ui.todorecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.CompoundButton.OnCheckedChangeListener
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hyeon.todolist.data.Todo
import com.hyeon.todolist.databinding.TodolistItemBinding

class TodoListAdapter(var todoList : ArrayList<Todo>,
                      val checkedChangeListener: OnItemCheckedChangeListener, ) : Adapter<TodoListAdapter.TodoListViewHoler> (){

    class TodoListViewHoler(val binding : TodolistItemBinding) : ViewHolder(binding.root) {

        fun bind(todo : Todo,
                 checkedChangeListener : OnItemCheckedChangeListener,){

            binding.checkboxTodo.setOnCheckedChangeListener{ _, isChecked ->checkedChangeListener.onItemCheckedChange(isChecked)}
            binding.checkboxTodo.isChecked = todo.isDo

            binding.textViewTodo.text = todo.content

            binding.imageViewMore.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHoler {
        val binding = TodolistItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TodoListViewHoler(binding)
    }

    override fun onBindViewHolder(holder: TodoListViewHoler, position: Int) {
        holder.bind(todoList[position],checkedChangeListener)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }
}

interface OnItemCheckedChangeListener{
    fun onItemCheckedChange(isCheck : Boolean)
}
