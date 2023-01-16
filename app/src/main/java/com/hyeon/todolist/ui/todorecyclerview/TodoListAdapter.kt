package com.hyeon.todolist.ui.todorecyclerview

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable.Orientation
import android.text.InputType
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.CheckBox
import android.widget.EditText
import android.widget.FrameLayout.LayoutParams
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.inputmethod.EditorInfoCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.hyeon.todolist.R
import com.hyeon.todolist.Todo
import com.hyeon.todolist.databinding.TodolistItemBinding
import com.hyeon.todolist.ui.MainActivity
import com.hyeon.todolist.ui.ManageTodoBottomSheet

class TodoListAdapter(val context : Context) : RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder>(){
    private var todos : List<Todo> = listOf(
        Todo("운동","","",false),
        Todo("공부","","",false)
    )

    inner class TodoListViewHolder(val binding : TodolistItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(type : String){

            binding.buttonAdd.apply{
                text = type
                setOnClickListener{
                    makeTodoEditText()
                }
            }
        }

        private fun makeTodoEditText(content : String = ""){
            val linearLayout = LinearLayout(context)
            val linearLayoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT)
            val checkBox = CheckBox(context)
            val editText = EditText(context)
            val imageView = ImageView(context)
            val checkBoxLayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            val editTextLayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            val imageViewLayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)

            linearLayout.orientation = LinearLayout.HORIZONTAL
            editTextLayoutParams.weight = 1F
            imageViewLayoutParams.gravity = Gravity.CENTER_VERTICAL

            checkBox.apply {
                background = ContextCompat.getDrawable(context,R.drawable.selector_todo_checkbox)
                buttonDrawable = ContextCompat.getDrawable(context,android.R.color.transparent)
                layoutParams = checkBoxLayoutParams

                setOnCheckedChangeListener { compoundButton, isChecked ->
                    if (isChecked){

                        /** EditText View 정렬하기 */
                    }
                }
            }

            /** 할일 내용 작성 EditText  */
            editText.apply {
                setText(content)
                layoutParams = editTextLayoutParams
                imeOptions = EditorInfo.IME_ACTION_DONE
                isSingleLine = true
                /** Listener */
                setOnEditorActionListener { view, keyCode, keyEvent ->
                    val content = view.text
                    /** 완료 버튼 클릭시 */
                    if (keyCode == EditorInfo.IME_ACTION_DONE){
                        /** 빈 내용인 경우 */
                        if(content.isEmpty()){
                            binding.layoutRoot.removeView(linearLayout)

                        }
                        /** 내용이 있는 경우 */
                        else {
                            /** view 를 변경 불가능한 상태로 만들기 */
                            view.apply {
                                isFocusable = false
                                isCursorVisible = false
                                keyListener = null
                                setBackgroundColor(Color.TRANSPARENT)
                                /** 클릭 시 BottomSheetDialog 띄우기 */
                                setOnClickListener {
                                    ManageTodoBottomSheet().show(
                                        (context as MainActivity).supportFragmentManager,
                                        ManageTodoBottomSheet.TAG)

                                }
                            }
                        }
                    }
                    false
                }
            }

            imageView.apply {
                setImageResource(R.drawable.ic_baseline_more_horiz_24)
                layoutParams = imageViewLayoutParams
            }

            linearLayout.apply {
                addView(checkBox)
                addView(editText)
                addView(imageView)
            }
            binding.layoutRoot.addView(linearLayout)

        }

    }


    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : TodoListViewHolder{
        val binding = TodolistItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TodoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        holder.bind(todos[position].type)
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}
