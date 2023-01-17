package com.hyeon.todolist.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyeon.todolist.R
import com.hyeon.todolist.databinding.FragmentHomeBinding
import com.hyeon.todolist.ui.todorecyclerview.TodoListAdapter
import com.hyeon.todolist.viewmodel.TodoViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter
import java.util.*

private val TAG = "HomeFragment"
class HomeFragment : Fragment(){
    private lateinit var binding : FragmentHomeBinding
    private lateinit var mActivity : MainActivity
    private var isMonthMode : Boolean = true
    private lateinit var mTodoViewModel : TodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mActivity = activity as MainActivity        // Fragment 와 연결된 Activity Context

        /** viewModel의 초기화*/
        mTodoViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
            .create(TodoViewModel::class.java)

        var position : Int = 0
        with(binding){
            /** 단위 ( 월, 주 ) 선택하는 버튼 초기화 및 이벤트 리스너 등록 */
            buttonChangeMW.apply {
                setButton()
                setOnClickListener {
                    isMonthMode = !isMonthMode
                    setButton()
                    setCalendar()
                }
            }

            calendarView.apply{
                state().edit().setFirstDayOfWeek(Calendar.MONDAY).commit()  // 첫번 째 요일 : 월요일
                setTitleFormatter(MonthArrayTitleFormatter(resources.getStringArray(R.array.custom_months)))    // 월 ( 한글 Format )
                setWeekDayFormatter(ArrayWeekDayFormatter(resources.getStringArray(R.array.custom_weekdays)))   // 요일 ( 한글 Format )
                selectedDate = CalendarDay.today()  // 기본 선택 날짜 : 오늘

                /** 날짜 클릭 (변경)시 Event Listener
                 *  widget : 달력 ( Material Calendar View ) ,
                 *  date : 선택 날짜 ( CalendarDay ),
                 *  selected : 선택 여부 ( Boolean ) */
                setOnDateChangedListener { widget, date, selected ->
                    /** To do 목록 변경 */
                }
            }
            recyclerViewTodoList.adapter = TodoListAdapter(mActivity)
            recyclerViewTodoList.layoutManager = LinearLayoutManager(mActivity)
        }
    }

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