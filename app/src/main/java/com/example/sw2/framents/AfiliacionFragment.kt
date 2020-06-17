package com.example.sw2.framents

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.example.sw2.patrones_diseño.singleton.FirebaseConexion
import com.example.sw2.Clases.Usuario
import com.example.sw2.R
import com.example.sw2.interfaces.toolbar_transaction
import com.example.sw2.interfaces.translate_fragment
import com.example.sw2.patrones_diseño.factory_Register.RegisterAfiliado
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.InternalCoroutinesApi

class AfiliacionFragment: Fragment() {
    //Inicialización de vista
    private lateinit var vista : View
    //varaibless interfaces
    private var variable_cambio_fragment: translate_fragment? = null
    private var int_toolbar_trans_afiliacionfrag:toolbar_transaction? = null
    //Widgets del xml
    private var botonSubir: Button? = null
    private var TextViewTittle:TextView? = null
    private var Toolbar_AfiliacionFragment: Toolbar ? = null
    // Varaible para para conexión con Firebase
    private lateinit var authFirbase:FirebaseAuth
    private var FirebaseConexion: FirebaseConexion? = null
    private lateinit var FirebaseDatabase:FirebaseDatabase
    private var user: Usuario? = null
    private var afiliado: Boolean? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        variable_cambio_fragment = activity as translate_fragment
        int_toolbar_trans_afiliacionfrag = activity as toolbar_transaction
    }
    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Escogiendo que tipo de fragment se mostrara////
        this.FirebaseConexion = com.example.sw2.patrones_diseño.singleton.FirebaseConexion.getinstance(requireContext())
        user = FirebaseConexion?.getStoreSaved()
        vista = if (!user?.Afiliado!!){
            inflater.inflate(R.layout.fragment_noafiliacion,container,false)
        }else{
            inflater.inflate(R.layout.fragment_afiliacion,container,false)
        }

        ///////////////////////////////////////////////////////////////////////
        var button:Button? = vista.findViewById(R.id.button_afiliarse_noafiliado)
        TextViewTittle = vista.findViewById(R.id.textView_title_noafiliado)
        ///////////Inicialización de toolbar///////////////////////
        setHasOptionsMenu(true)
        int_toolbar_trans_afiliacionfrag?.change_tittle("Afiliación")


        TextViewTittle?.text = "!"+ user!!.Nombre + " todavia no te afilias!"
        button?.setOnClickListener {
            variable_cambio_fragment?.cambiar_fragment("RegisterAfiliado")
        }
        return vista
    }
    private fun retrieveDataProfileAfiliado(){
        var uriImagen: String? = null
        FirebaseConexion =
            FirebaseConexion(
                requireContext()
            )
        var ObjectUser = FirebaseConexion.getStoreSaved()
        Toast.makeText(requireContext(),ObjectUser?.Email.toString(),Toast.LENGTH_SHORT).show()
        var query: Query =
            com.google.firebase.database.FirebaseDatabase.getInstance().reference.child("User").orderByChild("E-mail")
                .equalTo(ObjectUser?.Email)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(p0: DataSnapshot) {
                for (p0 in p0.children) {
                    ViewName.text = p0.child("Nombre").value.toString()
                    ViewLastname.text = p0.child("Apellido").value.toString()
                    ViewEmail.text = p0.child("E-mail").value.toString()
                    ViewPhone.text = p0.child("Telefono").value.toString()
                    Glide.with(v)
                        .load(p0.child("UrlImagen").value.toString().toUri())
                        .fitCenter()
                        .centerCrop()
                        .into(ImageViewProfile)
                }
            }
        })
    }

}
