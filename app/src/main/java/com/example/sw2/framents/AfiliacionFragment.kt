package com.example.sw2.framents

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.sw2.Clases.Servicio_profile_afiliacion
import com.example.sw2.patrones_diseño.singleton.FirebaseConexion
import com.example.sw2.Clases.Usuario
import com.example.sw2.R
import com.example.sw2.interfaces.toolbar_transaction
import com.example.sw2.interfaces.translate_fragment
import com.example.sw2.patrones_diseño.factory.ReclyceViewAdapter_ServiciosHome
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
    private var vista_value :String? = null
    private var botonSubir: Button? = null
    private var TextViewTittle:TextView? = null
    private var Toolbar_AfiliacionFragment: Toolbar ? = null
    private var tvi_enterprisename_porifle: TextView? = null
    private var tvi_email_profile:TextView? = null
    private var tvi_cant_servicios_value: TextView? =null
    private var tvi_dealsdone_profile:TextView? = null
    private var tvi_distrito_local_value:TextView? = null
    private var tvi_distrito_local:TextView? = null
    private var tvi_phone_local_value:TextView? = null
    private var iv_circle_view_profile_afiliado:ImageView? = null
    private var iv_addService: ImageButton? = null
    // Varaible para para conexión con Firebase
    private lateinit var authFirbase:FirebaseAuth
    private var FirebaseConexion: FirebaseConexion? = null
    private lateinit var FirebaseDatabase:FirebaseDatabase
    private var user: Usuario? = null
    private var afiliado: Boolean? = null
    /////
    private var test_arreglo: ArrayList<Servicio_profile_afiliacion>? = null
    companion object {
        val GALERY_INTENT = 1
        private val IMAGE_PICK_CODE: Int = 1000
        private lateinit var  ReclyceViewAdapter_Servicio_profile_afiliacion : Servicio_profile_afiliacion
    }
    @InternalCoroutinesApi
    override fun onAttach(context: Context) {
        super.onAttach(context)
        variable_cambio_fragment = activity as translate_fragment
        int_toolbar_trans_afiliacionfrag = activity as toolbar_transaction
        ////////////////////////////
        this.FirebaseConexion = com.example.sw2.patrones_diseño.singleton.FirebaseConexion.getinstance(requireContext())
        user = FirebaseConexion?.getStoreSaved()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        ///////////Inicialización de toolbar///////////////////////
        int_toolbar_trans_afiliacionfrag?.change_tittle("Afiliación")
        //Escogiendo que tipo de fragment se mostrara////
        vista = if (!user?.Afiliado!!){
            inflater.inflate(R.layout.fragment_noafiliacion,container,false)
        }else{
            inflater.inflate(R.layout.fragment_afiliacion,container,false)
        }

        if (user?.Afiliado!!){
            tvi_enterprisename_porifle = vista.findViewById(R.id.tvi_enterprisename_porifle)
            tvi_distrito_local_value = vista.findViewById(R.id.tvi_distrito_local_value)
            tvi_email_profile = vista.findViewById(R.id.tvi_email_profile)
            tvi_phone_local_value = vista.findViewById(R.id.tvi_phone_local_value)
            iv_addService = vista.findViewById(R.id.add_service_profile_fragment)
            iv_circle_view_profile_afiliado = vista.findViewById(R.id.circle_view_profile_afiliado)
            iv_circle_view_profile_afiliado?.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                Log.d("action pick", Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, ProfileFragment.GALERY_INTENT)
            }
            iv_addService?.setOnClickListener {
                Log.d("dewbug","asd")
                variable_cambio_fragment?.cambiar_fragment("RegistrarNewServiceFragment")
            }
            retrieveDataProfileAfiliado()



        }else{
            var button:Button? = vista.findViewById(R.id.button_afiliarse_noafiliado)
            TextViewTittle = vista.findViewById(R.id.textView_title_noafiliado)
            TextViewTittle?.text = "!"+ user!!.Nombre + " todavia no te afilias!"
            button?.setOnClickListener {
                variable_cambio_fragment?.cambiar_fragment("RegisterAfiliado")
            }

        }
        //*********************************************************\\

        ///////////////////////////////////////////////////////////////////////

        return vista
    }
    private fun arreglo_test(){
        test_arreglo = ArrayList(listOf(Servicio_profile_afiliacion("serivicio de prueba")))
    }
    private fun recly(){

    }
    private fun retrieveDataProfileAfiliado(){
        FirebaseConexion =
            FirebaseConexion(
                requireContext()
            )
        val ObjectUser = FirebaseConexion!!.getStoreSaved()
        Toast.makeText(requireContext(),ObjectUser?.Email.toString(),Toast.LENGTH_SHORT).show()
        Log.d("IDafilaiado",ObjectUser!!.IDAfiliado.toString())
        val query: Query =
            com.google.firebase.database.FirebaseDatabase.getInstance().reference.child("Afiliados").orderByChild("ID_Afiliado")
                .equalTo(ObjectUser.IDAfiliado)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(p0: DataSnapshot) {
                Toast.makeText(requireContext(),"e",Toast.LENGTH_SHORT).show()
                for (p0 in p0.children) {
                    Log.d("Empresa_servicio",p0.child("Distrito_servicio").value.toString())
                    tvi_enterprisename_porifle?.text = p0.child("Empresa_servicio").value.toString()
                    tvi_distrito_local_value?.text = p0.child("Distrito_servicio").value.toString()
                    tvi_email_profile?.text = p0.child("Email_servicio").value.toString()
                    tvi_phone_local_value?.text = p0.child("Telefono_servicio").value.toString()
                    tvi_cant_servicios_value?.text = p0.child("cant_servicios").value.toString()
                    tvi_dealsdone_profile?.text = p0.child("contratos_realizados").value.toString()
                    Glide.with(vista)
                        .load(p0.child("URI_Imagen_Serivcio").value.toString().toUri())
                        .fitCenter()
                        .centerCrop()
                        .into(iv_circle_view_profile_afiliado!!)
                }
            }
        })
    }

}
