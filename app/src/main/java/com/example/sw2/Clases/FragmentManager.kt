package com.example.sw2.Clases

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.sw2.R

class FragmentManager : AppCompatActivity() {
    companion object{
        lateinit var FragmentManager:FragmentTransaction
    }
    init {

    }
    fun createTransasction (selectedFragment:Fragment): Boolean {
        FragmentManager = supportFragmentManager.beginTransaction()
        FragmentManager.replace(R.id.fragment_container,selectedFragment).commit()
        return true
    }
}