package com.example.sw2


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.sw2.framents.AfiliacionFragment
import com.example.sw2.framents.ContratosFragment
import com.example.sw2.framents.HomeFragment
import com.example.sw2.framents.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.w3c.dom.Text


class MainActivity : AppCompatActivity() {
    private lateinit var selectedFragment : Fragment

    private val navListener: BottomNavigationView.OnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {item ->
            when (item.itemId) {
                R.id.nav_home -> selectedFragment = HomeFragment()
                R.id.nav_afiliación -> selectedFragment = AfiliacionFragment()
                R.id.nav_Busqueda -> selectedFragment = SearchFragment()
                R.id.nav_contratos -> selectedFragment = ContratosFragment()
            }
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit()
            true
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)
        bottomNav.selectedItemId = R.id.nav_home.toInt()

    }
}
