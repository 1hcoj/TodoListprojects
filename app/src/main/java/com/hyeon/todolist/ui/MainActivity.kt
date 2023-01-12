package com.hyeon.todolist.ui

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.hyeon.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.imageViewButtonMenu.setOnClickListener{
            binding.layoutDrawer.openDrawer(GravityCompat.END)
        }
    }
    /** 이거만 이상하게 override에서 오류나길래 지금 안쓰는거라 주석처리해놨음*/
    /*
    override fun onNavigationItemSelected(item : MenuItem):Boolean{
        when(item.itemId){

        }
        return false
    }
     */

    override fun onBackPressed(){
        if (binding.layoutDrawer.isDrawerOpen(GravityCompat.END)){
            binding.layoutDrawer.closeDrawers()
        }
        else{
            super.onBackPressed()
        }
    }
}