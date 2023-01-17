package com.hyeon.todolist.ui

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hyeon.todolist.R
import com.hyeon.todolist.databinding.BottomSheetManageTodoBinding
import com.hyeon.todolist.viewmodel.TodoViewModel
import com.hyeon.todolist.viewmodel.TodoViewModelFactory

class ManageTodoBottomSheet(val itemClick : (String)-> Unit) : BottomSheetDialogFragment() {
    lateinit var binding : BottomSheetManageTodoBinding

    private val viewModel : TodoViewModel by activityViewModels{
        TodoViewModelFactory((activity?.application) as Application)
    }

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
            buttonModify.setOnClickListener {
                itemClick("MODIFY")
                dialog?.dismiss()
            }
            buttonRemove.setOnClickListener {
                itemClick("REMOVE")
                dialog?.dismiss()
            }
            layoutNoticeTime.setOnClickListener {  }
            layoutDoTomorrow.setOnClickListener {  }
            layoutChangeDay.setOnClickListener {  }
            layoutMoveLibrary.setOnClickListener {  }
        }


    }
}