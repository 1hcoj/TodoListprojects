package com.hyeon.todolist.ui

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.hyeon.todolist.R
import com.hyeon.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding : ActivityMainBinding
    private val navController by lazy {
        Navigation.findNavController(this, R.id.fragmentContainerView)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            /** navi_menu.xml 에서 해당하는 아이템 Id -> navigate() */
            R.id.goalFragment -> navController.navigate(R.id.action_homeFragment_to_goalFragment)

        }
        binding.layoutDrawer.closeDrawers()
        return false
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

    override fun onBackPressed(){
        if (binding.layoutDrawer.isDrawerOpen(GravityCompat.END)){
            binding.layoutDrawer.closeDrawers()
        }
        else{
            super.onBackPressed()
        }
    }
}