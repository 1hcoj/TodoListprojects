package com.hyeon.todolist.ui

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyeon.todolist.R
import com.hyeon.todolist.data.Todo
import com.hyeon.todolist.databinding.FragmentHomeBinding
import com.hyeon.todolist.ui.todorecyclerview.TodoListAdapter
import com.hyeon.todolist.viewmodel.TodoViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter
import java.util.*
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ListAdapter
import com.google.android.material.navigation.NavigationView
import com.hyeon.todolist.databinding.ActivityMainBinding
import kotlin.collections.ArrayList

class HomeFragment : Fragment(),IOnBackPressed,NavigationView.OnNavigationItemSelectedListener{

    private lateinit var binding : FragmentHomeBinding
    private val mActivity : MainActivity by lazy{
        activity as MainActivity
    }
    private var isMonthMode : Boolean = true
    private lateinit var mTodoViewModel : TodoViewModel
    private val navController by lazy {
        Navigation.findNavController(this.requireView())
    }

    /** Navigation Menu 선택 이벤트 처리 */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding.layoutDrawer.closeDrawers()
        when(item.itemId){
            R.id.goalFragment->{ navController.navigate(R.id.action_homeFragment_to_goalFragment) }
        }
        return false
    }
    /** 휴대폰의 Back 버튼 클릭 이벤트 처리 */
    override fun onBackPressed() :Boolean{
        Log.d("디버그",binding.layoutDrawer.isDrawerOpen(GravityCompat.END).toString())
        return if(binding.layoutDrawer.isDrawerOpen(GravityCompat.END)){
            binding.layoutDrawer.closeDrawers()
            true
        }else{
            false
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** viewModel 의 초기화*/
        mTodoViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
            .create(TodoViewModel::class.java)

        with(binding){
            /** 달력 레이아웃 방식 선택 버튼  */
            buttonChangeMW.apply {
                setButton()
                setOnClickListener {
                    isMonthMode = !isMonthMode
                    setButton()
                    setCalendar()
                }
            }

            /** 달력 */
            calendarView.apply{
                state().edit().setFirstDayOfWeek(Calendar.MONDAY).commit()  // 첫번 째 요일 : 월요일
                setTitleFormatter(MonthArrayTitleFormatter(resources.getStringArray(R.array.custom_months)))    // 월 ( 한글 Format )
                setWeekDayFormatter(ArrayWeekDayFormatter(resources.getStringArray(R.array.custom_weekdays)))   // 요일 ( 한글 Format )
                selectedDate = mTodoViewModel.selectedDay

                /** 날짜 클릭 (변경)시 Event Listener
                 *  widget : 달력 ( Material Calendar View ) ,
                 *  date : 선택 날짜 ( CalendarDay ),
                 *  selected : 선택 여부 ( Boolean ) */
                setOnDateChangedListener { _, calDate, _ ->
                    mTodoViewModel.updateDate(calDate)
                }
            }

            /** 메뉴 버튼 */
            buttonMenu.setOnClickListener {
                layoutDrawer.openDrawer(GravityCompat.END)
            }

            navigationView.setNavigationItemSelectedListener(this@HomeFragment)     // 네비게이션

            /** 목표 + TodoList 목록 */
            val adapter = TodoListAdapter()

            recyclerViewTodoList.adapter = adapter
            recyclerViewTodoList.layoutManager = LinearLayoutManager(mActivity)

            mTodoViewModel.todoList.observe(viewLifecycleOwner){ todo->
                todo.let {
                    adapter.submitList(it)
                }
            }
        }
    }

    /** Calendar 월, 주 상태 변화 */
    private fun setCalendar() {
        val calendarMode = if (isMonthMode) CalendarMode.MONTHS else CalendarMode.WEEKS

        binding.calendarView.state().edit().setCalendarDisplayMode(calendarMode).commit()
    }

    /** 월, 주 캘린더 정보 변경 버튼 클릭시 버튼 변화 */
    private fun setButton() {
        val term : String
        val buttonImageRsc : Int

        if (isMonthMode){
            term = "월"
            buttonImageRsc = R.drawable.ic_baseline_arrow_drop_up_24
        }else {
            term = "주"
            buttonImageRsc = R.drawable.ic_baseline_arrow_drop_down_24
        }

        binding.buttonChangeMW.apply {
            text = term
            setCompoundDrawablesWithIntrinsicBounds(0, 0, buttonImageRsc, 0)
        }
    }

}

interface IOnBackPressed{
    fun onBackPressed() : Boolean
}