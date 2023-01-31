package com.hyeon.todolist.ui.todorecyclerview

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyeon.todolist.databinding.TimeItemBinding

class HourAdapter : RecyclerView.Adapter<HourAdapter.HourViewHolder>() {

    private val hourList = listOf(1,2,3,4,5,6,7,8,9,10,11,12)

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : HourViewHolder {
        val binding = TimeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HourViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int = hourList.size

    inner class HourViewHolder(val binding : TimeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position : Int){
            binding.tvTimeItem.text = hourList[position].toString()
        }
    }
}