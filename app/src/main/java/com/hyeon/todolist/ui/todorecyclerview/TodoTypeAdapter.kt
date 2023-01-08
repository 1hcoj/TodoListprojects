package com.hyeon.todolist.ui.todorecyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hyeon.todolist.data.Todo
import com.hyeon.todolist.databinding.TodotypeItemBinding

class TodoTypeAdapter(val todoTypeList : ArrayList<String>, val listener : OnItemClickListener) : RecyclerView.Adapter<TodoTypeAdapter.TodoTypeViewHolder> (){

    class TodoTypeViewHolder(val binding : TodotypeItemBinding): ViewHolder(binding.root){

        fun bind(typeName : String,listener: OnItemClickListener,position : Int){
            binding.buttonType.text = typeName

            binding.buttonType.setOnClickListener {
                Log.d("디버그",position.toString())
                listener.onItemClick(position) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoTypeViewHolder {
        val binding = TodotypeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  TodoTypeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoTypeViewHolder, position: Int) {
        holder.bind(todoTypeList[position],listener,position)


    }

    override fun getItemCount(): Int {
        return todoTypeList.size
    }
}

interface OnItemClickListener{
    fun onItemClick(position : Int)
}