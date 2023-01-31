package com.hyeon.todolist.ui

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView
import com.hyeon.todolist.R
import com.hyeon.todolist.databinding.ActivityMainBinding
import com.hyeon.todolist.util.OnBackPressedListener

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{
    private lateinit var binding : ActivityMainBinding
    val navController by lazy {
        Navigation.findNavController(this, R.id.fragmentContainerView)
    }

    private lateinit var onBackPressedListener : OnBackPressedListener

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            /** navi_menu.xml 에서 해당하는 아이템 Id -> navigate() */
            R.id.goal -> {
                navController.navigate(R.id.action_homeFragment_to_goalFragment)
                return true
            }
            R.id.timer -> {
                navController.navigate(R.id.action_homeFragment_to_timeFragment)
                binding.layoutDrawer.closeDrawers()
                return true
            }
            else -> {
                binding.layoutDrawer.closeDrawers()
                return false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.imageViewButtonMenu.setOnClickListener{
            binding.layoutDrawer.openDrawer(GravityCompat.END)
        }
        binding.navigationView.setNavigationItemSelectedListener(this)

    }

    private fun setOnBackPressedListener(onBackPressedListener : OnBackPressedListener){
        this.onBackPressedListener = onBackPressedListener
    }

    override fun onBackPressed(){
        if (binding.layoutDrawer.isDrawerOpen(GravityCompat.END)){
            binding.layoutDrawer.closeDrawers()
        }
        else{
            super.onBackPressed()
        }
    }

    /* navigation bar를 보이거나 숨기는 함수 */
    fun HideNavigation(state : Boolean){
        if (state){
            binding.navigationView.visibility = View.GONE
        } else{
            binding.navigationView.visibility = View.VISIBLE
        }
    }

    /* bottom dialog 생성 */
    fun createDialog(b : BottomSheetDialogFragment){
        b.show(supportFragmentManager, b.tag)
    }
}