package com.hyeon.todolist.ui.todorecyclerview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.InputType
import android.text.method.KeyListener
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.CheckBox
import android.widget.EditText
import android.widget.FrameLayout.LayoutParams
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hyeon.todolist.R
import com.hyeon.todolist.Todo
import com.hyeon.todolist.databinding.TodolistItemBinding
import com.hyeon.todolist.ui.MainActivity
import com.hyeon.todolist.ui.ManageTodoBottomSheet
import com.hyeon.todolist.viewmodel.TodoViewModel

class TodoListAdapter(val context : Context, val viewModel : TodoViewModel) : RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder>(){

    /** Todo
     *  1. DB 에서 전체 타입 받아오기
     *  2. type List 로 하나의 View Holder 생성
     *  3. EditText 는 type 과 selectedDay 에 해당하는 할일 List 로 생성 */

    private var todos : List<List<Todo>> = listOf(
        listOf(
            Todo("운동","푸쉬업100개","",false),
            Todo("운동","풀업 100개","",false)
        ),
        listOf(Todo("공부","안드로이드 ","",false))
    )

    inner class TodoListViewHolder(private val binding : TodolistItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private var type : String = ""
        fun bind(type : String,todoList : List<Todo>){
            this.type = type
            binding.buttonAdd.apply{
                text = type
                setOnClickListener{
                    makeTodoEditText()
                }
            }
            todoList.forEachIndexed { _, todo ->
                makeTodoEditText(todo.content)
            }
        }
        /** EditText 동적 생성 */
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
            linearLayout.layoutParams = linearLayoutParams
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

                layoutParams = editTextLayoutParams
                imeOptions = EditorInfo.IME_ACTION_DONE
                isSingleLine = true

                setText(content)
                if (content != ""){
                    makeEditDisable(this,linearLayout)
                }

                setOnEditorActionListener { view, keyCode, keyEvent ->
                    val content = view.text
                    /** 작성 완료 버튼 클릭시 */
                    if (keyCode == EditorInfo.IME_ACTION_DONE){
                        /** 빈 내용인 경우 */
                        if(content.isEmpty()){
                            binding.layoutRoot.removeView(linearLayout)
                        } else {
                            makeEditDisable(view,linearLayout)
                            /** DB 에 저장하는  코드 작성*/
                            viewModel.insert(Todo(
                                type,
                                content.toString(),
                                viewModel.selectedDayFormat,
                                false))

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
        /** EditText 작성 불가 설정 */
        private fun makeEditDisable(textView: TextView,container : LinearLayout){
            textView.apply {
                inputType = InputType.TYPE_NULL
                backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)

                /** 클릭 시 BottomSheetDialog 띄우기 */
                setOnFocusChangeListener { view, isFocused ->
                    if (isFocused){
                        val manageTodoBottomSheet = ManageTodoBottomSheet{ result ->

                            when(result){
                                "MODIFY"->{
                                    makeEditAble(textView)
                                    textView.requestFocus()
                                }
                                "REMOVE"->{
                                    binding.layoutRoot.removeView(container)
                                    /** Todo DB 에서 해당 Data 삭제 */
                                }
                            }
                        }
                        manageTodoBottomSheet.show(
                            (context as MainActivity).supportFragmentManager,
                            ManageTodoBottomSheet.TAG)
                    }

                }
            }
        }

        private fun makeEditAble(textView: TextView){
            textView.apply {
                inputType = EditText(context).inputType
                onFocusChangeListener = null
                /** Todo 키보드도 자동으로 올라오는 기능 */
            }
        }
    }


    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : TodoListViewHolder{
        val binding = TodolistItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TodoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        holder.bind(todos[position][0].type,todos[position])
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}
