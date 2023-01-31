package com.hyeon.todolist.ui.todorecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyeon.todolist.databinding.TimeItemBinding

class MinuteAdapter : RecyclerView.Adapter<MinuteAdapter.MinuteViewHolder>(){
    private val minuteList = listOf(0,5,10,15,20,25,30,35,40,45,50,55)

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : MinuteAdapter.MinuteViewHolder {
        val binding = TimeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MinuteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MinuteAdapter.MinuteViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int = minuteList.size

    inner class MinuteViewHolder(val binding : TimeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position : Int){
            binding.tvTimeItem.text = minuteList[position].toString()
        }
    }
}

