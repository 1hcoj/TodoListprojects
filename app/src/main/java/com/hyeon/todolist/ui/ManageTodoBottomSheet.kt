package com.hyeon.todolist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hyeon.todolist.R
import com.hyeon.todolist.databinding.BottomSheetManageTodoBinding

class ManageTodoBottomSheet : BottomSheetDialogFragment() {
    lateinit var binding : BottomSheetManageTodoBinding

    companion object{
        const val TAG = "ManageTodoBottomSheet"
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetManageTodoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            buttonModify.setOnClickListener {}
            buttonRemove.setOnClickListener {}
            layoutNoticeTime.setOnClickListener {}
            layoutDoTomorrow.setOnClickListener {}
            layoutChangeDay.setOnClickListener {}
            layoutMoveLibrary.setOnClickListener {}
        }
    }
}