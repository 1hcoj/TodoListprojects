package com.hyeon.todolist.ui.todorecyclerview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.InputType
import android.text.method.KeyListener
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.CheckBox
import android.widget.EditText
import android.widget.FrameLayout.LayoutParams
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.hyeon.todolist.R
import com.hyeon.todolist.data.Todo
import com.hyeon.todolist.databinding.TodolistItemBinding
import com.hyeon.todolist.ui.MainActivity
import com.hyeon.todolist.ui.ManageTodoBottomSheet
import com.hyeon.todolist.ui.typeKey
import com.hyeon.todolist.viewmodel.TodoViewModel


class TodoListAdapter(val context : Context, val viewModel : TodoViewModel,val lifecycleOwner : LifecycleOwner) : RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder>(){

    private var types : List<String> = listOf()

    init {
        viewModel.getType().observe(lifecycleOwner, Observer {
            types = it
        })
    }
    inner class TodoListViewHolder(private val binding : TodolistItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private var type : String = ""

        /** 함수명 : bind
         *  매개변수 : type : String ( 목표 ), todoList : List<할일>? ( 목표에 해당하는 할일 리스트 )
         *  리턴형 : X
         *  함수 설명 :
         *      1. type 을 사용하여 할일 추가 Button 을 생성한다.
         *      2. todoList 를 사용하여 각 type 에 해당하는 할일 목록 Layout 을 생성한다.
         *  */
        fun bind(type : String,todoList : List<Todo>?){

            this.type = type

            binding.buttonAdd.apply{
                text = type
                setOnClickListener{
                    makeTodoEditText()
                }
            }

            todoList?.forEachIndexed { _, todo ->
                if (todo.content != typeKey)  makeTodoEditText(todo)
            }

        }

        /** 함수명 : makeTodoEditText
         *  매개변수 : todo : Todo
         *  리턴형 : X
         *  함수 설명 :
         *      1. 할일 목록 CheckBox( 달성 여부 ), EditText( 내용 ), ImageView( 더 보기 ) 생성
         *      2. 매개변수 Default 값을 null 로 처리하여  DB에 존재하는 데이터와 새로 생성하는 데이터 구분
         *      3. 기존에 존재하던 Data 의 변경 : 104 ~ 125
         *      4. 새로 생성하는 Data 의 추가 : 127 ~ 145
         * */
        private fun makeTodoEditText(todo : Todo?=null){
            val content = todo?.content ?: ""
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
                val check = todo?.isChecked ?: false
                background = ContextCompat.getDrawable(context,R.drawable.selector_todo_checkbox)
                buttonDrawable = ContextCompat.getDrawable(context,android.R.color.transparent)
                layoutParams = checkBoxLayoutParams
                isChecked = check
                /** Check Box 선택은 Data 가 DB 에 저장됨을 가정 */
                setOnCheckedChangeListener { compoundButton, isChecked ->
                    var findTodo = viewModel.getTodo(type,editText.text.toString()).value!!
                    findTodo.isChecked = isChecked

                    viewModel.update(findTodo)
                    /** Todo EditText View 정렬하기 */


                }
            }

            editText.apply {
                layoutParams = editTextLayoutParams
                imeOptions = EditorInfo.IME_ACTION_DONE
                isSingleLine = true

                setText(content)

                /** DB 에 저장된 기존 할일 **/
                if (content != "") {
                    makeEditDisable(this, linearLayout)

                    setOnEditorActionListener { view, keyCode, _ ->

                        if (keyCode == EditorInfo.IME_ACTION_DONE) {
                            val content = view.text

                            if (content.isEmpty()) {                        // 내용이 없는 경우
                                binding.layoutRoot.removeView(linearLayout)
                                viewModel.delete(todo!!)
                            } else {
                                makeEditDisable(view, linearLayout)              // 작성 불가 설정
                                if (todo!!.content != content.toString()) todo!!.content = content.toString()
                                viewModel.update(todo!!)

                            }
                        }
                        false
                    }
                }
                /** 새로 생성하는 할일  */
                else {

                    setOnEditorActionListener { view, keyCode, _ ->

                        if (keyCode == EditorInfo.IME_ACTION_DONE) {
                            val content = view.text

                            if (content.isEmpty()) {
                                binding.layoutRoot.removeView(linearLayout)
                            } else {
                                makeEditDisable(view, linearLayout)
                                viewModel.insert(Todo(type, content.toString(), viewModel.selectedDayFormat, false))
                            }
                        }
                        false
                    }
                    requestFocus()
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

        /** 함수명 : makeEditDisable
         *  매개변수 : textView : TextView ( EditText )  , container : LinearLayout (부모 레이아웃 )
         *  리턴형 : X
         *  함수 설명 :
         *      1. EditText 를 입력 불가능한 상태로 설정한다
         *      2. EditText 를 클릭 시, BottomSheetDialog 를 띄움
         *      3. BottomSheetDialog 와 Adapter 사이의 데이터 상호작용
         * */
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
                                    val todo = viewModel.getTodo(type,text.toString()).value!!
                                    viewModel.delete(todo)
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

        /** 함수명 : makeEditAble
         *  매개변수 : textView : TextView ( EditText )
         *  리턴형 : X
         *  함수 설명 :
         *      1. EditText 를 입력 가능한 상태로 설정한다.
         *  */
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

    /** 함수명 : onBindViewHolder
     *  매개변수 : holder : TodoListViewHolder ( Recyclerview 의 한 화면 원소 ) , position : Int ( 인덱스 )
     *  리턴형 : X
     *  함수 설명 :
     *      1. 사용자가 선택한 날짜의 todoList Data 를 화면과 Binding 한다.
     * */
    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        var todos : List<Todo> = listOf()
        viewModel.getTodolist(types[position],viewModel.selectedDayFormat).observe(
            lifecycleOwner, Observer { todos = it })
        holder.bind(types[position],todos)
    }

    override fun getItemCount(): Int {
        return types.size
    }
}
