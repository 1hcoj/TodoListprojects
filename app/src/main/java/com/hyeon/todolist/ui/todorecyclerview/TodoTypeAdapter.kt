package com.hyeon.todolist.ui.todorecyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyeon.todolist.databinding.TodotypeItemBinding

class TodoTypeAdapter(val listener : OnItemClickListener) : RecyclerView.Adapter<TodoTypeAdapter.TodoTypeViewHolder> (){

    private var todoTypes : List<String> = listOf()

    inner class TodoTypeViewHolder(val binding : TodotypeItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(type : String, listener: OnItemClickListener, position : Int){
            binding.buttonType.text = type

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
        val todo = todoTypes[position]
        holder.bind(todo,listener,position)

    }

    override fun getItemCount(): Int {
        return todoTypes.size
    }
    /** data 변경 시 */
    fun setTodoTypes(todoTypes : List<String>){
        this.todoTypes = todoTypes
        notifyDataSetChanged()
    }
}

interface OnItemClickListener{
    fun onItemClick(position : Int)
}