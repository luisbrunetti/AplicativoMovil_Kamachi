package com.example.sw2.framents.secundarios

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.sw2.Clases.Afiliado
import com.example.sw2.Clases.Usuario
import com.example.sw2.R
import com.example.sw2.framents.ProfileFragment
import com.example.sw2.interfaces.translate_fragment
import com.example.sw2.patrones_diseño.singleton.FirebaseConexion
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_register_newservice.*
import kotlinx.coroutines.InternalCoroutinesApi

class RegistrarNewServiceFragment:Fragment(){
    private lateinit var vistaRegisterNewService: View
    //Firebase
    private var Auth:FirebaseAuth? = null
    private var FirebaseReal: FirebaseDatabase? =null
    private var FirebaseStorage:FirebaseStorage? = null
    /// XML
    private var iv_newService: ImageButton? = null
    private var ete_servicename_newservice: EditText? = null
    private var ete_timeduration_newservice: EditText? = null
    private var ete_costservice_newservice: EditText? = null
    private var ete_description_newservice: EditText? =null
    private var but_verify_newservice:Button? =null
    ///////////////////////////
    private var int_translatefragment_newservice:translate_fragment? = null
    //////////////////////
    private var user_profile: Usuario? = null
    private var afiliado:Afiliado? = null
    ///Varaibles para el guardado en Firebase
    private var uriImagenAndroid:Uri? = null
    private var ImagenServicio:Uri?= null
    private var keypush:String? = null
    private var referenciaDatabaseF:DatabaseReference? = null
    companion object {
        private val GALERY_INTENT = 1
        private val IMAGE_PICK_CODE: Int = 1000
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        int_translatefragment_newservice = activity as translate_fragment
    }
    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        user_profile = FirebaseConexion.getinstance(requireActivity()).getStoreSaved()
        vistaRegisterNewService = inflater.inflate(R.layout.fragment_register_newservice,container,false)
        FirebaseReal = FirebaseDatabase.getInstance()
        FirebaseStorage = com.google.firebase.storage.FirebaseStorage.getInstance()

    /////////////////////////////////////////////
        afiliado = arguments?.getSerializable("afiliado") as Afiliado
        Log.d("fragment",afiliado?.Email_servicio.toString())

        //########33
        iv_newService =vistaRegisterNewService.findViewById(R.id.iv_new_service)
        ete_servicename_newservice= vistaRegisterNewService.findViewById(R.id.ete_servicename_newservice)
        ete_timeduration_newservice = vistaRegisterNewService.findViewById(R.id.ete_timeduration_newservice)
        ete_costservice_newservice = vistaRegisterNewService.findViewById(R.id.ete_costservice_newservice)
        ete_description_newservice = vistaRegisterNewService.findViewById(R.id.ete_description_newservice)
        but_verify_newservice = vistaRegisterNewService.findViewById(R.id.but_verify_newservice)
        referenciaDatabaseF = FirebaseReal?.reference
        keypush = referenciaDatabaseF!!.push().key
        iv_newService?.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            Log.d("action pick", Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,
                GALERY_INTENT
            )
        }
        but_verify_newservice?.setOnClickListener {
            SaveDataofNewService()
        }


        return vistaRegisterNewService
    }
    private fun SaveDataofNewService() {
        progressbar_newservice.visibility = View.VISIBLE
        if (ete_servicename_newservice!!.text.isNotEmpty() && ete_timeduration_newservice!!.text.isNotEmpty() &&
            ete_costservice_newservice!!.text.isNotEmpty() && ete_description_newservice!!.text.isNotEmpty()
        ) {
            val refService = referenciaDatabaseF!!.child("Servicios")
                .child(keypush!!)
            val filePath: StorageReference =
                FirebaseStorage!!.reference.child("ImagenServicios/" + keypush.toString())
                    .child("Fotoservicio.png")
            filePath.putFile(ImagenServicio!!).addOnSuccessListener { t ->
                val downloadURL = t.metadata?.reference?.downloadUrl
                downloadURL?.addOnCompleteListener { task ->
                    if (task.isComplete && task.isSuccessful) {
                        uriImagenAndroid = downloadURL.result!!
                        Toast.makeText(
                            requireContext(),
                            "Se guardo la imagen exitosamente",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.d("refServicio", refService.toString())
                        refService.child("nombreTrabaj")
                            .setValue(ete_servicename_newservice!!.text.toString())
                        refService.child("duracion")
                            .setValue(ete_timeduration_newservice!!.text.toString())
                        refService.child("cost_service")
                            .setValue(ete_costservice_newservice!!.text.toString())
                        refService.child("description")
                            .setValue(ete_description_newservice!!.text.toString())
                        refService.child("distrito")
                            .setValue(afiliado?.Distrito_servicio.toString())
                        refService.child("telefono").setValue(afiliado!!.Telefono_servicio)
                        refService.child("Email_servicio").setValue(afiliado!!.Email_servicio)
                        refService.child("Categoria_servicio")
                            .setValue(afiliado!!.Categoria_servicio)
                        refService.child("Tipo_persona").setValue(afiliado!!.Tipo_de_persona)
                        refService.child("calificacion").setValue(null)
                        refService.child("Uri").setValue(uriImagenAndroid.toString())
                        refService.child("key").setValue(keypush)
                        refService.child("ID_Afiliado").setValue(afiliado!!.ID_afiliado)
                        refService.child("calificacion").setValue("0")
                        if(afiliado!!.Tipo_de_persona == "Persona Juridica" ){
                            refService.child("Nombre_empresa").setValue(afiliado!!.Empresa_servicio)
                        }else{
                            /*refService.child("Nombres_servicio").setValue(afiliado!!.)
                            refService.child("Apellidos_servicio")
                            refService.child("DNI")*/
                        }

                        Toast.makeText(
                            requireContext(),
                            "El nuevo servici se ha creado correctamente",
                            Toast.LENGTH_SHORT
                        ).show()
                        progressbar_newservice.visibility = View.GONE

                        int_translatefragment_newservice?.cambiar_fragment(
                            "AfiliacionFragment",
                            null
                        )
                        ///////////////////////////////////////////////////////////////////////////////////////////////////
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Ocurrio un error al descargar la imagen",
                            Toast.LENGTH_LONG
                        )
                            .show()
                        progressbar_newservice.visibility = View.GONE
                    }
                }
            }
        } else {
            progressbar_newservice.visibility = View.GONE
            Toast.makeText(requireContext(), "Llene los campos correctamente", Toast.LENGTH_SHORT)
                .show()
        }
    }
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            ImagenServicio = data?.data
            if (requestCode == ProfileFragment.GALERY_INTENT && resultCode == Activity.RESULT_OK) {
                if (ImagenServicio != null) {
                    Glide.with(requireContext())
                        .load(ImagenServicio)
                        .fitCenter()
                        .centerCrop()
                        .into(iv_newService!!)
                }
            }
        }
    }

