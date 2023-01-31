package com.hyeon.todolist.ui

import android.os.Bundle
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
        /*
        이 fragment 메뉴에서 다시 이 fragment를 선택했을 떄 오류, 추가적으로 이 fragment에서 다른 메뉴를 눌러 fragment 전환을 하고자 한다면 어떻게 해야할까ㅓ??

        >> 메뉴에 달린 event를 다시 재설정 하거나 혹은 현재 fragment를 넘겨줘서 에러를 띄우도록?
         */
    }

    override fun onDestroy() {
        super.onDestroy()
        mActivity.HideNavigation(false)
    }

    override fun onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment: Boolean) {
        super.onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment)
    }

    override fun onBackPressed() {
        if (this is TimeFragment){
        }
        parentFragmentManager.beginTransaction().remove(this).commit()
    }
}