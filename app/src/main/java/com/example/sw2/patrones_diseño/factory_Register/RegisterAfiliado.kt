package com.example.sw2.patrones_diseño.factory_Register

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils.*
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sw2.patrones_diseño.singleton.FirebaseConexion
import com.example.sw2.R
import com.example.sw2.framents.ProfileFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.FirebaseStorage.*
import com.google.firebase.storage.StorageReference

class RegisterAfiliado : AppCompatActivity() {
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_afiliado)

        FirebaseConexion =
            FirebaseConexion(
                applicationContext
            )
        var user = FirebaseConexion.getStoreSaved()
        FirebaseReal = FirebaseDatabase.getInstance()
        toolbar = findViewById(R.id.toolbar_register_afiliado)
        radiobuttonPI = findViewById(R.id.radioButton_personaindependiente)
        radiobuttonPJ = findViewById(R.id.radioButton_personajuridica)
        Spinner = findViewById(R.id.spinner_registerafiliado)
        Spinner_categoria = findViewById(R.id.spinner_categoria_registro_afiliado)
        textNombre = findViewById(R.id.Nombres_register_servicio)
        textApellido = findViewById(R.id.Apellidos_register_servicio)
        textTelefono= findViewById(R.id.editext_Telefono_Servicio)
        textempresa = findViewById(R.id.empresa_register_servicio)
        textemail = findViewById(R.id.editext_Email_Servicio)
        textRUc = findViewById(R.id.RUC_register_servicio)
        textDni = findViewById(R.id.DNI_register_servicio)
        buttonCrear = findViewById(R.id.id_Crear_afiliado_button)
        ImagenViewAfiliado = findViewById(R.id.imageView_Register_Afiliado)
        //Inicialización de boton de crear afiliado
        buttonCrear?.setOnClickListener {
            //Asignación de los valores


            if(!SpinnerDistritoValue.equals("Escoge el distrito del servicio") && !SpinnerCategoriaValue.equals("Selecione la categoria")
            ){
                var radioButtonBooleanPI: Boolean = false
                var radioButtonBooleanPJ:Boolean = false
                var referenciaFirebase = FirebaseReal.reference
                var key = FirebaseReal.reference.push().key
                var ref = referenciaFirebase.child("Afilados").child(key!!)
                var refuser = referenciaFirebase.child("User").child(user?.ID.toString())
                refuser.child("ID_Afiliado").setValue(key!!)
                refuser.child("Afiliado").setValue("true")
                FirebaseConexion.UpdateValue("true","Afiliado")
                if(ImagenViewAfiliado?.drawable.toString() == "android.graphics.drawable.VectorDrawable@789df4e"){
                    Toast.makeText(applicationContext,"Debe ingresar una imagen referencial para el servicio",Toast.LENGTH_LONG).show()
                }else{
                    ref.child("Tipo de persona").setValue(RadioButton_Value)
                    if(RadioButton_Value.equals("Persona Independiente")){
                        Log.d("test ", (isEmpty(textNombre.toString()) || isEmpty(textApellido.toString())  || isEmpty(textDni.toString())).toString())
                        Log.d("testnombre ", (isEmpty(textNombre?.toString()).toString()))
                        Log.d("testnombre ", (isEmpty(textApellido?.toString()).toString()))
                        Log.d("testnombre ", (isEmpty(textDni?.toString()).toString()))
                        Log.d("testnombre ", textNombre?.text.toString())
                        if(textNombre?.text.toString().isNotEmpty() && textApellido?.text.toString().isNotEmpty() && textDni?.text.toString().isNotEmpty()){
                            radioButtonBooleanPI = true
                            }else{
                            Toast.makeText(applicationContext,"Falta llenar campos para registrarse",Toast.LENGTH_LONG).show()
                        }
                    }else{
                        if(textempresa?.text.toString().isNotEmpty() && textRUc?.text.toString().isNotEmpty()){
                            radioButtonBooleanPJ = true
                        }else{
                            Toast.makeText(applicationContext,"Falta llenar campos para registrarse",Toast.LENGTH_LONG).show()
                        }
                    }
                    if(textemail?.text.toString().isNotEmpty() && textTelefono?.text.toString().isNotEmpty()){
                        if(radioButtonBooleanPI){
                            ref.child("Nombres_servicio").setValue(textNombre?.text.toString())
                            ref.child("Apellidos_servicio").setValue(textApellido?.text.toString())
                            ref.child("DNI").setValue(textDni?.text.toString())
                        }else if(radioButtonBooleanPJ){
                            ref.child("Empresa_servicio").setValue(textempresa?.text.toString())
                            ref.child("RUC").setValue(textRUc?.text.toString())
                        }
                        ref.child("Email_servicio").setValue(textemail?.text.toString())
                        ref.child("Telefono_servicio").setValue(textTelefono?.text.toString())
                        ref.child("Distrito_servicio").setValue(SpinnerDistritoValue)
                        ref.child("Categoria_servicio").setValue(SpinnerCategoriaValue)
                        ref.child("URI_Imagen_Serivcio").setValue(Uri.toString())
                        ref.child("ID_Usuario_node").setValue(user?.ID.toString())
                        ref.child("ID_Afiliado").setValue(key!!)
                        Toast.makeText(applicationContext,"Se creo tu cuenta de afilado correctamente",Toast.LENGTH_LONG).show()
                        /*var a = MainActivity()
                        a.ReloadFragment()*/



                    }else{
                        Toast.makeText(applicationContext,"Falta llenar campos para registrarse",Toast.LENGTH_LONG).show()
                    }
                }
            }
            else{
                Toast.makeText(applicationContext,"Tiene que seleccionar el distrito o el tipo de categoria",Toast.LENGTH_LONG).show()
            }

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
        var adaptadorSpinnerCategoria  = ArrayAdapter.createFromResource(applicationContext,
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


        var adaptadorSpinner = ArrayAdapter.createFromResource(applicationContext,
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
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //inicializando variables Firebase
        FirebaseReal = FirebaseDatabase.getInstance()
        FirebaseStorage = getInstance()
    }
    fun cambiarimagen(view :View){
        val intent = Intent(Intent.ACTION_PICK)
        Log.d("action pick", Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,
            GALERY_INTENT
        )
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Auth = FirebaseAuth.getInstance()

        val user = Auth.currentUser!!
        val userID = user.uid
        var ImagenName: String? = null
        val uri: Uri? = data?.data
        idPush = FirebaseReal.reference.push().key
        if (requestCode == ProfileFragment.GALERY_INTENT && resultCode == Activity.RESULT_OK) {
            ImagenName = "FotoPerfilAfiliado"
            var filePath: StorageReference =
                FirebaseStorage.reference.child("FotosAfiliado/" + idPush + "/" + ImagenName)
                    .child("FotoPerfilAfiliado.jpg")
            if (uri != null) {
                filePath.putFile(uri).addOnSuccessListener { t->
                    var downloadURL = t.metadata?.reference?.downloadUrl
                    downloadURL?.addOnCompleteListener { task->
                        Uri = downloadURL?.result!!
                        //SaveUriImagenInFireBase(Uri,userID)
                        if(task.isComplete && task.isSuccessful){
                            Glide.with(applicationContext)
                                .load(Uri)
                                .fitCenter()
                                .apply(RequestOptions.overrideOf(160,160))
                                .centerCrop()
                                .into(ImagenViewAfiliado!!)
                            Toast.makeText(applicationContext, "Se guardo la imagen exitosamente", Toast.LENGTH_LONG)
                                .show()
                        }else{
                            Toast.makeText(applicationContext, "Ocurrio al descargar la imagen", Toast.LENGTH_LONG)
                                .show()
                        }
                    }

                }
            }
        }
    }
}
