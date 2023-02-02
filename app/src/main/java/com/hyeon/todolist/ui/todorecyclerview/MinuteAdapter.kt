package com.hyeon.todolist.ui.todorecyclerview

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyeon.todolist.databinding.TimeItemBinding
import com.hyeon.todolist.ui.AlarmPlusFragment

class MinuteAdapter : RecyclerView.Adapter<MinuteAdapter.MinuteViewHolder>(){
    private val minuteList = listOf(0,5,10,15,20,25,30,35,40,45,50,55)
    private var dataIndex = 0

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : MinuteAdapter.MinuteViewHolder {
        val binding = TimeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MinuteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MinuteViewHolder, position: Int) {
        holder.bind(position)
        setData(holder)
        holder.itemView.setOnClickListener{
            notifyItemChanged(dataIndex)
            dataIndex = holder.adapterPosition
            notifyItemChanged(dataIndex)
            setData(holder)
            onItemClickListener.onItemClick(minuteList[position])
        }
    }
    override fun getItemCount(): Int = minuteList.size

    inner class MinuteViewHolder(val binding : TimeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position : Int){
            binding.tvTimeItem.text = minuteList[position].toString()
        }
    }
    private fun setData(holder : MinuteViewHolder){
        if (dataIndex == holder.adapterPosition){
            holder.binding.tvTimeItem.setBackgroundColor(Color.parseColor("#FF2828CD"))
        } else{
            holder.binding.tvTimeItem.setBackgroundColor(Color.parseColor("#FFb4b4b4"))
        }
    }

    interface OnItemClickListener{
        fun onItemClick(_minute : Int)
    }

    private lateinit var onItemClickListener : OnItemClickListener

    fun setOnItemClickListener(listener : OnItemClickListener){
        this.onItemClickListener = listener
    }
}