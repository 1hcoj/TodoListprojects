package com.hyeon.todolist.ui

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hyeon.todolist.R
import com.hyeon.todolist.databinding.FragmentAlarmPlusBinding
import com.hyeon.todolist.ui.todorecyclerview.HourAdapter
import com.hyeon.todolist.ui.todorecyclerview.MinuteAdapter

class AlarmPlusFragment : BottomSheetDialogFragment() {

    var selectTime : String = ""
    private var hour : Int = 0
    private var minute : Int = 0

    private lateinit var binding : FragmentAlarmPlusBinding
    private val mHourAdapter by lazy{
        HourAdapter()
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
        val gray = getColor(requireContext(),R.color.light_gray)
        val blue = getColor(requireContext(),R.color.blue)
        binding.apply{
            btnAM.setOnClickListener{
                if (selectTime == "PM"){
                    btnPM.setBackgroundColor(gray)
                }
                selectTime = "AM"
                btnAM.setBackgroundColor(blue)
            }
            btnPM.setOnClickListener{
                if (selectTime == "AM"){
                    btnAM.setBackgroundColor(gray)
                }
                selectTime = "PM"
                btnPM.setBackgroundColor(blue)
            }
            btnDone.setOnClickListener{
                val bundle = Bundle()
                val fragment = TimeFragment()
                bundle.putInt("hour",hour)
                bundle.putInt("minute",minute)
                bundle.putString("time",selectTime)
                fragment.arguments = bundle
                dismiss()
            }
        }
    }

    private fun initRecyclerView(){
        binding.apply{
            hourContainer.apply{
                adapter = mHourAdapter
                mHourAdapter.setOnItemClickListener(object : HourAdapter.OnItemClickListener{
                    override fun onItemClick(_hour: Int) {
                        hour = _hour
                    }
                })
                val hourLayout = GridLayoutManager(context,6)
                layoutManager = hourLayout
                setHasFixedSize(true)
                val hourDecorationSize = RecyclerDecorationSize(20, 5)
                addItemDecoration(hourDecorationSize)
            }

            minuteContainer.apply{
                adapter = mMinuteAdapter
                mMinuteAdapter.setOnItemClickListener(object : MinuteAdapter.OnItemClickListener{
                    override fun onItemClick(_minute : Int){
                        minute = _minute
                    }
                })
                val minuteLayout = GridLayoutManager(context, 6)
                layoutManager = minuteLayout
                setHasFixedSize(true)
                val minuteDecorationSize = RecyclerDecorationSize(20,5)
                addItemDecoration(minuteDecorationSize)
            }
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

    interface OnDoneClickListener {
        fun onDoneClick(_hour : Int, _minute : Int, _time : String)
    }

    private lateinit var onDoneClickListener : OnDoneClickListener

    fun setOnDoneClickListener(listener : OnDoneClickListener){
        this.onDoneClickListener = listener
    }
}