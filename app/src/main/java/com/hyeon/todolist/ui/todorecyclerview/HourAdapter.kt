package com.hyeon.todolist.ui.todorecyclerview

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hyeon.todolist.R
import com.hyeon.todolist.databinding.FragmentAlarmPlusBinding
import com.hyeon.todolist.databinding.TimeItemBinding
import com.hyeon.todolist.ui.AlarmPlusFragment

class HourAdapter() : RecyclerView.Adapter<HourAdapter.HourViewHolder>() {

    private val hourList = listOf(1,2,3,4,5,6,7,8,9,10,11,12)
    private var dataIndex = 0
    private lateinit var binding : TimeItemBinding

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : HourViewHolder {
        binding = TimeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HourViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        holder.bind(position)
        setData(holder)
        holder.itemView.setOnClickListener{
            notifyItemChanged(dataIndex)
            dataIndex = holder.adapterPosition
            notifyItemChanged(dataIndex)
            setData(holder)
            onItemClickListener.onItemClick(hourList[position])
        }
    }
    override fun getItemCount(): Int = hourList.size

    inner class HourViewHolder(val binding : TimeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position : Int){
            binding.tvTimeItem.text = hourList[position].toString()
        }
    }
    private fun setData(holder : HourViewHolder){
        if (dataIndex == holder.adapterPosition){
            holder.binding.tvTimeItem.setBackgroundColor(Color.parseColor("#FF2828CD"))
        } else{
            holder.binding.tvTimeItem.setBackgroundColor(Color.parseColor("#FFb4b4b4"))
        }
    }
    private lateinit var onItemClickListener : OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(_hour : Int)
    }

    fun setOnItemClickListener(listener : OnItemClickListener){
        this.onItemClickListener = listener
    }
}