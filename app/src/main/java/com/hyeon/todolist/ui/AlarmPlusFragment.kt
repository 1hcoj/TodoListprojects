package com.hyeon.todolist.ui

import android.graphics.Rect
import android.os.Bundle


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hyeon.todolist.databinding.FragmentAlarmPlusBinding
import com.hyeon.todolist.ui.todorecyclerview.HourAdapter
import com.hyeon.todolist.ui.todorecyclerview.MinuteAdapter

class AlarmPlusFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentAlarmPlusBinding
    private val mHourAdapter by lazy{
        HourAdapter(requireContext())
    }
    private val mMinuteAdapter by lazy{
        MinuteAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAlarmPlusBinding.inflate(layoutInflater,container,false)

        initRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply{

        }
    }

    private fun initRecyclerView(){
        binding.apply{
            hourContainer.adapter = mHourAdapter
            minuteContainer.adapter = mMinuteAdapter

            val hourLayout = GridLayoutManager(context,6)
            val minuteLayout = GridLayoutManager(context, 6)
            hourContainer.layoutManager = hourLayout
            minuteContainer.layoutManager = minuteLayout
            hourContainer.setHasFixedSize(true)
            minuteContainer.setHasFixedSize(true)

            val hourDecorationSize = RecyclerDecorationSize(20, 5)
            val minuteDecorationSize = RecyclerDecorationSize(20,5)
            hourContainer.addItemDecoration(hourDecorationSize)
            minuteContainer.addItemDecoration(minuteDecorationSize)
        }
    }
    class RecyclerDecorationSize(val divHeight : Int, val divWidth : Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            if (parent.getChildAdapterPosition(view) != parent.adapter?.itemCount){
                outRect.bottom = divHeight
            }
            outRect.right = divWidth
        }
    }
}