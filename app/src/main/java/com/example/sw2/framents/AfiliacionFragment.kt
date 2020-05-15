package com.example.sw2.framents

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.sw2.patrones_diseño.singleton.FirebaseConexion
import com.example.sw2.Clases.Usuario
import com.example.sw2.R
import com.example.sw2.Secundarios.RegisterAfiliado
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.InternalCoroutinesApi

class AfiliacionFragment: Fragment() {
    private var FirebaseConexion: FirebaseConexion? = null
    private lateinit var FirebaseDatabase:FirebaseDatabase
    private var botonSubir: Button? = null
    private lateinit var vista : View
    private var TextViewTittle:TextView? = null
    // Varaible para la autentificación en Firebase
    private lateinit var authFirbase:FirebaseAuth
    private var user: Usuario? = null
    private var afiliado: Boolean? = null

    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        this.FirebaseConexion = com.example.sw2.patrones_diseño.singleton.FirebaseConexion.getinstance(requireContext())
        user = FirebaseConexion?.getStoreSaved()

        Log.d("userrr", user.toString())


        vista = if (!user?.Afiliado!!){
            inflater.inflate(R.layout.fragment_noafiliacion,container,false)
        }else{
            inflater.inflate(R.layout.fragment_afiliacion,container,false)
        }
        var button:Button? = vista.findViewById(R.id.button_afiliarse_noafiliado)
        TextViewTittle = vista.findViewById(R.id.textView_title_noafiliado)
        TextViewTittle?.text = "!"+ user!!.Nombre + " todavia no te afilias!"
        button?.setOnClickListener {
            startActivity(Intent(requireContext(), RegisterAfiliado::class.java))
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////

        return vista
    }
}
