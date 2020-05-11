package com.example.sw2


import android.app.SearchManager
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.example.sw2.Clases.FirebaseConexion
import com.example.sw2.Secundarios.EditarPefil
import com.example.sw2.framents.AfiliacionFragment
import com.example.sw2.framents.ContratosFragment
import com.example.sw2.framents.HomeFragment
import com.example.sw2.framents.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import android.view.MenuItem as MenuItem

class MainActivity : AppCompatActivity() {
    private lateinit var selectedFragment : Fragment
    private lateinit var viewPager :ViewPager
    private lateinit var emailUsuario : String
    private lateinit var FirebaseConexion:FirebaseConexion
    private lateinit var fragment:Fragment

    private val navListener: BottomNavigationView.OnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {item ->
            //Obteniendo email desde un intent
            when (item.itemId) {
                R.id.nav_home ->
                    selectedFragment = HomeFragment()
                R.id.nav_afiliación -> selectedFragment = AfiliacionFragment()
                R.id.nav_Perfil -> selectedFragment = ProfileFragment()
                R.id.nav_contratos -> selectedFragment = ContratosFragment()
            }

            supportFragmentManager.beginTransaction().replace(R.id.fragment_container,selectedFragment).commit()

            true
        }
    fun editarperfil(view: View){
        startActivity(Intent(this, EditarPefil::class.java))
    }
    companion object {
        var bottomNav : BottomNavigationView? = null

        fun ReloadFragment(){

        }

    }
    init{
        
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav?.setOnNavigationItemSelectedListener(navListener)
        bottomNav?.selectedItemId = R.id.nav_home
        bottomNav?.menu?.findItem(R.id.nav_home)?.isEnabled = false
        FirebaseConexion= FirebaseConexion(applicationContext)

    }

    override fun onStart() {
        super.onStart()

    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(applicationContext,"onresume",Toast.LENGTH_SHORT).show()

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navbar_navigation,menu)
        val searchManager =getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val itembusqueda =menu?.findItem(R.id.app_bar_search)
        //val searchView = itembusqueda?.actionView as SearchView
        return super.onCreateOptionsMenu(menu)
    }
}
