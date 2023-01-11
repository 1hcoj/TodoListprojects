package com.hyeon.todolist.ui.todorecyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyeon.todolist.Todo
import com.hyeon.todolist.databinding.TodotypeItemBinding

class TodoTypeAdapter(val listener : OnItemClickListener) : RecyclerView.Adapter<TodoTypeAdapter.TodoTypeViewHolder> (){

    private var todoItems : List<Todo> = listOf()

    inner class TodoTypeViewHolder(val binding : TodotypeItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(todo : Todo, listener: OnItemClickListener, position : Int){
            binding.buttonType.text = todo.type

            binding.buttonType.setOnClickListener {
                Log.d("디버그",position.toString())
                listener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoTypeViewHolder {
        val binding = TodotypeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  TodoTypeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoTypeViewHolder, position: Int) {
        val todo = todoItems[position]
        holder.bind(todo,listener,position)

    }

    override fun getItemCount(): Int {
        return todoItems.size
    }
}

interface OnItemClickListener{
    fun onItemClick(position : Int)
}