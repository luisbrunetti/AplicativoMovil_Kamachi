package com.example.sw2.framents.secundarios

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sw2.Clases.Usuario
import com.example.sw2.patrones_diseño.singleton.FirebaseConexion
import com.example.sw2.R
import com.example.sw2.framents.ProfileFragment
import com.example.sw2.interfaces.toolbar_transaction
import com.example.sw2.interfaces.translate_fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.FirebaseStorage.*
import com.google.firebase.storage.StorageReference

class RegisterAfiliadoFragment : Fragment() {
    //VIEEW
    private lateinit var v_frag_RegisterAfiliado: View
    //Interface cambio fragment
    private var change_frag_RegisterAfiliado:translate_fragment? = null
    private var int_toolbar_transaction_RegisterAfi:toolbar_transaction? =null
    private lateinit var Spinner: Spinner
    private lateinit var Spinner_categoria : Spinner
    private var Uri : Uri? = null
    //Firebase
    private lateinit var Auth:FirebaseAuth
    private lateinit var FirebaseStorage:FirebaseStorage
    private lateinit var FirebaseReal:FirebaseDatabase
    private lateinit var FirebaseConexion : FirebaseConexion
    private var idPush:String? = null
    //Widgets del registro
    var user:Usuario ? = null
    private var radiobuttonPI:RadioButton? = null
    private var radiobuttonPJ:RadioButton?=null
    private var textNombre:EditText? = null
    private var textApellido:EditText? = null
    private var textTelefono:EditText?= null
    private var textempresa:EditText? = null
    private var textemail:EditText? = null
    private var textRUc:EditText? = null
    private var textDni:EditText? = null
    private var ImagenViewAfiliado:ImageView? = null
    private var toolbar:Toolbar? = null
    private var buttonCrear:Button? = null
    //Valores de los Spinners
    private var SpinnerDistritoValue :String? = null
    private var SpinnerCategoriaValue:String? = null
    //Valores de los radio button
    private var RadioButton_Value: String? = null
    companion object {
        private val GALERY_INTENT = 1
        private val IMAGE_PICK_CODE: Int = 1000
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        change_frag_RegisterAfiliado = activity as translate_fragment
        int_toolbar_transaction_RegisterAfi = activity as toolbar_transaction
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v_frag_RegisterAfiliado = inflater.inflate(R.layout.fragment_register_afiliado,container,false)
        FirebaseConexion =
            FirebaseConexion(
                requireContext()
            )
        user = FirebaseConexion.getStoreSaved()
        FirebaseReal = FirebaseDatabase.getInstance()
        radiobuttonPI = v_frag_RegisterAfiliado.findViewById(R.id.radioButton_personaindependiente)
        radiobuttonPJ = v_frag_RegisterAfiliado.findViewById(R.id.radioButton_personajuridica)
        Spinner = v_frag_RegisterAfiliado.findViewById(R.id.spinner_registerafiliado)
        Spinner_categoria = v_frag_RegisterAfiliado.findViewById(R.id.spinner_categoria_registro_afiliado)
        textNombre = v_frag_RegisterAfiliado.findViewById(R.id.Nombres_register_servicio)
        textApellido = v_frag_RegisterAfiliado.findViewById(R.id.Apellidos_register_servicio)
        textTelefono= v_frag_RegisterAfiliado.findViewById(R.id.editext_Telefono_Servicio)
        textempresa = v_frag_RegisterAfiliado.findViewById(R.id.empresa_register_servicio)
        textemail = v_frag_RegisterAfiliado.findViewById(R.id.editext_Email_Servicio)
        textRUc = v_frag_RegisterAfiliado.findViewById(R.id.RUC_register_servicio)
        textDni = v_frag_RegisterAfiliado.findViewById(R.id.DNI_register_servicio)
        buttonCrear = v_frag_RegisterAfiliado.findViewById(R.id.id_Crear_afiliado_button)
        ImagenViewAfiliado = v_frag_RegisterAfiliado.findViewById(R.id.imageView_Register_Afiliado)

        /////////////
        setHasOptionsMenu(true)
        int_toolbar_transaction_RegisterAfi?.change_tittle("Formulario de afiliación")
        ///////////////////////////////////////////////////////////
        ImagenViewAfiliado?.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            Log.d("action pick", Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,
                GALERY_INTENT
            )
        }
        //Inicialización de boton de crear afiliado
        buttonCrear?.setOnClickListener {
            //Asignación de los valores
            CrearNuevoAfiliado()
        }
        /////////////////////Inicialización de los radio button
        RadioButton_Value = "Persona Independiente"
        textNombre?.visibility = View.VISIBLE
        textApellido?.visibility = View.VISIBLE
        textDni?.visibility = View.VISIBLE
        Log.d("Imagenview",ImagenViewAfiliado?.drawable.toString())
        radiobuttonPJ?.setOnCheckedChangeListener { button, b ->
            if(b){
                textempresa?.visibility = View.VISIBLE
                textRUc?.visibility=View.VISIBLE

                RadioButton_Value = "Persona Juridica"
            }else{
                textempresa?.visibility = View.GONE
                textRUc?.visibility=View.GONE
            }
        }
        radiobuttonPI?.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                textNombre?.visibility = View.VISIBLE
                textApellido?.visibility = View.VISIBLE
                textDni?.visibility = View.VISIBLE
                RadioButton_Value = "Persona Independiente"
            }else{
                textNombre?.visibility = View.GONE
                textApellido?.visibility = View.GONE
                textDni?.visibility = View.GONE
            }
        }
        ////////////////////////////////////////////////////
        ////////Iniccialización del spinner///////////////
        var adaptadorSpinnerCategoria  = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.list_servicios_categoria,R.layout.spinner_text)
        adaptadorSpinnerCategoria.setDropDownViewResource(R.layout.spinner_text)
        Spinner_categoria.adapter = adaptadorSpinnerCategoria
        Spinner_categoria.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                SpinnerCategoriaValue = p0?.getItemAtPosition(p2).toString()
            }
        }
        var adaptadorSpinner = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.list_distritos_lima,
            R.layout.spinner_text
        )
        adaptadorSpinner.setDropDownViewResource(R.layout.spinner_text)
        Spinner.adapter = adaptadorSpinner

        Spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                SpinnerDistritoValue= p0?.getItemAtPosition(p2).toString()
            }

        }


        //Inicilización del toolbar
        toolbar?.title = "Formulario de registro"
        toolbar?.textAlignment = View.TEXT_ALIGNMENT_CENTER
        //Creando acitivity para que reconozca el toolbar
        var activity : AppCompatActivity = activity as AppCompatActivity
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)


        //inicializando variables Firebase
        FirebaseReal = FirebaseDatabase.getInstance()
        FirebaseStorage = getInstance()
        return v_frag_RegisterAfiliado
    }
    private fun CrearNuevoAfiliado(){
        val referenciaFirebase = FirebaseReal.reference
        val key = FirebaseReal.reference.push().key
        val ref = referenciaFirebase.child("Afiliados").child(key!!)
        val refuser = referenciaFirebase.child("User").child(user?.ID.toString())
        var radioButtonBooleanPI: Boolean = false
        var radioButtonBooleanPJ:Boolean = false
        if(!SpinnerDistritoValue.equals("Escoge el distrito del servicio") && !SpinnerCategoriaValue.equals("Selecione la categoria")
        ){
            if(ImagenViewAfiliado?.drawable.toString() == "android.graphics.drawable.VectorDrawable@789df4e"){
                Toast.makeText(requireContext(),"Debe ingresar una imagen referencial para el servicio",Toast.LENGTH_LONG).show()
            }else{
                if(RadioButton_Value.equals("Persona Independiente")){
                    /*Log.d("test ", (isEmpty(textNombre.toString()) || isEmpty(textApellido.toString())  || isEmpty(textDni.toString())).toString())
                    Log.d("testnombre ", (isEmpty(textNombre?.toString()).toString()))
                    Log.d("testnombre ", (isEmpty(textApellido?.toString()).toString()))
                    Log.d("testnombre ", (isEmpty(textDni?.toString()).toString()))
                    Log.d("testnombre ", textNombre?.text.toString())*/
                    if(textNombre?.text.toString().isNotEmpty() && textApellido?.text.toString().isNotEmpty() && textDni?.text.toString().isNotEmpty()){
                        radioButtonBooleanPI = true
                    }else{
                        Toast.makeText(requireContext(),"Falta llenar campos para registrarse",Toast.LENGTH_LONG).show()
                    }
                }else{
                    if(textempresa?.text.toString().isNotEmpty() && textRUc?.text.toString().isNotEmpty()){
                        radioButtonBooleanPJ = true
                    }else{
                        Toast.makeText(requireContext(),"Falta llenar campos para registrarse",Toast.LENGTH_LONG).show()
                    }
                }
                if(textemail?.text.toString().isNotEmpty() && textTelefono?.text.toString().isNotEmpty()){
                    refuser.child("ID_Afiliado").setValue(key)
                    refuser.child("Afiliado").setValue("true")
                    FirebaseConexion.UpdateValue("true","Afiliado")
                    FirebaseConexion.UpdateValue(key.toString(),"IDAfiliado")
                    Log.d("keyIDafiliado",FirebaseConexion.getStoreSaved()?.IDAfiliado!!)
                    if(radioButtonBooleanPI){
                        ref.child("Nombres_servicio").setValue(textNombre?.text.toString())
                        ref.child("Apellidos_servicio").setValue(textApellido?.text.toString())
                        ref.child("DNI").setValue(textDni?.text.toString())
                    }else if(radioButtonBooleanPJ){
                        ref.child("Empresa_servicio").setValue(textempresa?.text.toString())
                        ref.child("RUC").setValue(textRUc?.text.toString())
                    }
                    ref.child("Tipo de persona").setValue(RadioButton_Value)
                    ref.child("Email_servicio").setValue(textemail?.text.toString())
                    ref.child("Telefono_servicio").setValue(textTelefono?.text.toString())
                    ref.child("Distrito_servicio").setValue(SpinnerDistritoValue)
                    ref.child("Categoria_servicio").setValue(SpinnerCategoriaValue)
                    ref.child("URI_Imagen_Serivcio").setValue(Uri.toString())
                    ref.child("ID_Usuario_node").setValue(user?.ID.toString())
                    ref.child("ID_Afiliado").setValue(key!!)
                    ref.child("cant_servicio").setValue(0)
                    ref.child("contratos_realizados").setValue(0)
                    Toast.makeText(requireContext(),"Se creo tu cuenta de afilado correctamente",Toast.LENGTH_SHORT).show()
                    change_frag_RegisterAfiliado?.cambiar_fragment("AfiliacionFragment")
                }else{
                    Toast.makeText(requireContext(),"Falta llenar campos para registrarse",Toast.LENGTH_SHORT).show()
                }
            }
        }
        else{
            Toast.makeText(requireContext(),"Tiene que seleccionar el distrito o el tipo de categoria",Toast.LENGTH_SHORT).show()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Auth = FirebaseAuth.getInstance()

        val user = Auth.currentUser!!
        val userID = user.uid
        var Seccion: String? = null
        val uri: Uri? = data?.data
        idPush = FirebaseReal.reference.push().key
        if (requestCode == ProfileFragment.GALERY_INTENT && resultCode == Activity.RESULT_OK) {
            Seccion="FotoPerfilAfiliado"
            val filePath: StorageReference =
                FirebaseStorage.reference.child("FotosAfiliado/" + idPush + "/" + Seccion)
                    .child("FotoPerfilAfiliado.jpg")
            if (uri != null) {
                filePath.putFile(uri).addOnSuccessListener { t->
                    val downloadURL = t.metadata?.reference?.downloadUrl
                    downloadURL?.addOnCompleteListener { task->
                        Uri = downloadURL.result!!
                        //SaveUriImagenInFireBase(Uri,userID)
                        if(task.isComplete && task.isSuccessful){
                            Glide.with(requireContext())
                                .load(Uri)
                                .fitCenter()
                                .apply(RequestOptions.overrideOf(160,160))
                                .centerCrop()
                                .into(ImagenViewAfiliado!!)
                            Toast.makeText(requireContext(), "Se guardo la imagen exitosamente", Toast.LENGTH_LONG)
                                .show()
                        }else{
                            Toast.makeText(requireContext(), "Ocurrio al descargar la imagen", Toast.LENGTH_LONG)
                                .show()
                        }
                    }

                }
            }
        }
    }
}
