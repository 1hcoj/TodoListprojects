package com.hyeon.todolist.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hyeon.todolist.R
import com.hyeon.todolist.databinding.FragmentAlarmPlusBinding
import com.hyeon.todolist.databinding.FragmentTimeBinding
import com.hyeon.todolist.util.OnBackPressedListener

class TimeFragment : Fragment(), OnBackPressedListener {

    private lateinit var binding : FragmentTimeBinding
    lateinit var navController : NavController

    private val mActivity by lazy {
        activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentTimeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.HideNavigation(true)

        navController = Navigation.findNavController(view)

        /* + 버튼 누르면 alarmPlusFragment 띄우기 */
        binding.apply{
            btnPlus.setOnClickListener{
                val alarmPlusFragment = AlarmPlusFragment()
                mActivity.createDialog(alarmPlusFragment)
            }
            btnBack.setOnClickListener{
                navController.navigate(R.id.action_timeFragment_to_homeFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mActivity.HideNavigation(false)
    }

    override fun onStart(){
        super.onStart()
        val hour = arguments?.getInt("hour")
        val minute = arguments?.getInt("minute")
        val time = arguments?.getString("time")
        Log.d("TimeFragment","${hour},${minute},${time}")
    }

    override fun onBackPressed() {
        parentFragmentManager.beginTransaction().remove(this).commit()
    }

    /* text layout 만드는 함수 */
    fun makeTextView(){

    }
}