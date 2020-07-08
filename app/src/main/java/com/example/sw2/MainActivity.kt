package com.example.sw2


import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.example.sw2.Clases.Afiliado
import com.example.sw2.patrones_diseño.singleton.FirebaseConexion
import com.example.sw2.framents.AfiliacionFragment
import com.example.sw2.framents.ContratosFragment
import com.example.sw2.framents.HomeFragment
import com.example.sw2.framents.ProfileFragment

import com.example.sw2.interfaces.toolbar_transaction
import com.example.sw2.interfaces.translate_fragment
import com.example.sw2.framents.secundarios.RegisterAfiliadoFragment
import com.example.sw2.framents.secundarios.RegistrarNewServiceFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() ,translate_fragment,toolbar_transaction{
    private lateinit var selectedFragment : Fragment
    private lateinit var viewPager :ViewPager
    private lateinit var emailUsuario : String
    private lateinit var FirebaseConexion: FirebaseConexion
    private lateinit var fragment:Fragment
    private var toolbar_mainactivity: Toolbar? = null
    companion object {
        var bottomNav : BottomNavigationView? = null
    }
    private val navListener: BottomNavigationView.OnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {item ->
            //Obteniendo email desde un intent
            when (item.itemId) {
                R.id.nav_home -> selectedFragment = HomeFragment()
                R.id.nav_afiliación -> selectedFragment = AfiliacionFragment()
                R.id.nav_Perfil -> selectedFragment = ProfileFragment()
                //R.id.nav_contratos -> selectedFragment = ContratosFragment()
            }
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container,selectedFragment).commit()
            true
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav?.setOnNavigationItemSelectedListener(navListener)
        bottomNav?.selectedItemId = R.id.nav_home
        bottomNav?.menu?.findItem(R.id.nav_home)?.isEnabled = false
        toolbar_mainactivity = findViewById(R.id.toolbar_mainactivity)
        setSupportActionBar(toolbar_mainactivity)

    }

    override fun cambiar_fragment(fragment: String,afiliado:Afiliado?) {
        val supportFragment = supportFragmentManager.beginTransaction()
        when(fragment){
            "RegisterAfiliado" -> supportFragment.replace(R.id.fragment_container,
                RegisterAfiliadoFragment()
            ).commit()
            "AfiliacionFragment" -> supportFragment.replace(R.id.fragment_container,AfiliacionFragment()).commit()
            "RegistrarNewServiceFragment" -> RegistrarNuevoservicio(afiliado)//supportFragment.replace(R.id.fragment_container,RegistrarNewServiceFragment()).commit()
        }
    }
    private fun RegistrarNuevoservicio(afiliado:Afiliado?){
        val bundle = Bundle()
        val RegistrarNewServiceFragment = RegistrarNewServiceFragment()
        bundle.putSerializable("afiliado",afiliado!!)
        RegistrarNewServiceFragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,RegistrarNewServiceFragment).commit()


    }

    override fun change_tittle(tittle: String) {
        toolbar_mainactivity?.title = tittle
        setSupportActionBar(toolbar_mainactivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun change_menu() {
        TODO("Not yet implemented")
    }

    override fun finish_fragment() {
        Toast.makeText(applicationContext,"gaaaa",Toast.LENGTH_SHORT).show()
        AlertDialog.Builder(this).setTitle("¿Desea cerrar sesion?").setMessage("Si presiona ok, cerrará la sesión")
            .setPositiveButton(android.R.string.yes, object: DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    finish()
                }
            })
            .setNegativeButton(android.R.string.no,object: DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                }
            })
            .show()
    }
}
