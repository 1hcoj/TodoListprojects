package com.hyeon.todolist.ui.todorecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hyeon.todolist.data.Todo
import com.hyeon.todolist.databinding.TodoHeaderBinding
import com.hyeon.todolist.databinding.TodoItemBinding
import com.hyeon.todolist.viewmodel.TodoViewModel

class TodoListAdapter : ListAdapter<Todo, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    class HeaderViewHolder(private val binding: TodoHeaderBinding) : ViewHolder(binding.root) {
        fun bind(type : String){
            binding.buttonAdd.apply {
                text = type
                setOnClickListener {
                    /** Todo : Room DB 에 공백 Data 추가 */
                }
            }
        }
    }
    class ItemViewHolder(private val binding:TodoItemBinding) : ViewHolder(binding.root){
        fun bind(todo : Todo){
            with(binding){
                checkboxTodo.isChecked = todo.isChecked
                editTextTodo.setText(todo.content)
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (getItem(position).content == TodoViewModel.TYPE_KEY) TYPE_HEADER else TYPE_ITEM


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                HeaderViewHolder(
                    TodoHeaderBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
            TYPE_ITEM->{
                ItemViewHolder(
                    TodoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
            else ->{
                throw ClassCastException("Unknown viewType $viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentTodo = getItem(position)

        when (holder) {
            is HeaderViewHolder -> {
                holder.bind(currentTodo.type)
            }
            is ItemViewHolder -> {
                holder.bind(currentTodo)
            }
        }
    }

    companion object{
        private const val TYPE_HEADER : Int = 0
        private const val TYPE_ITEM : Int = 1
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Todo>(){
            override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem.content == newItem.content
            }
        }
    }
}
