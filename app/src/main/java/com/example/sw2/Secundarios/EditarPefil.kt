package com.example.sw2.Secundarios

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.sw2.Clases.FirebaseConexion
import com.example.sw2.Clases.Usuario
import com.example.sw2.R
import com.example.sw2.framents.ProfileFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class EditarPefil : AppCompatActivity() {
    private lateinit var InstanceFirebaseToSaveImage:FirebaseDatabase
    private lateinit var storageRef: FirebaseStorage
    private lateinit var toolbar_Edit : Toolbar
    private lateinit var emailUser:String
    private lateinit var ViewName: EditText
    private lateinit var ViewLastname: EditText
    private lateinit var ViewEmail: EditText
    private lateinit var ViewPhone: EditText
    private lateinit var checkboxName:CheckBox
    private lateinit var checkboxApellido:CheckBox
    private lateinit var checkboxEmail:CheckBox
    private lateinit var checkboxTelefono:CheckBox

    private lateinit var ImageViewProfile: ImageView
    private lateinit var FirebaseConexion:FirebaseConexion

    //Firebase
    private lateinit var Auth:FirebaseAuth
    //Datos del usuario
    private var UsuerInstance:Usuario? = null
    private var Uri: Uri? = null
    companion object {
        private val GALERY_INTENT = 1
        private val IMAGE_PICK_CODE: Int = 1000
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_pefil)
        //////////////////////////////////////////////
        storageRef = FirebaseStorage.getInstance()
        InstanceFirebaseToSaveImage = FirebaseDatabase.getInstance()
        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        ViewName = findViewById(R.id.textView_name_profile_edit)
        ViewLastname =findViewById(R.id.textViewLastName_profile_edit)
        ViewEmail =findViewById(R.id.textViewEmail_Profile_edit)
        ViewPhone = findViewById(R.id.textViewphone_profile_edit)
        checkboxName = findViewById(R.id.checkBox_name)
        checkboxApellido = findViewById(R.id.checkBox_lastname)
        checkboxEmail = findViewById(R.id.checkBox_email)
        checkboxTelefono = findViewById(R.id.checkBox_phone)
        ImageViewProfile = findViewById(R.id.imageView_profile_edit)
        var ButtonEditarFoto = findViewById<Button>(R.id.button_EditarFotoPerfil_edit)
        toolbar_Edit = findViewById(R.id.toolbar_edit)
        setSupportActionBar(toolbar_Edit)
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        FirebaseConexion = FirebaseConexion(applicationContext)
        UsuerInstance = FirebaseConexion.getStoreSaved()!!
        ViewName.setText(UsuerInstance!!.Nombre.toString(),TextView.BufferType.EDITABLE)
        ViewLastname.setText(UsuerInstance!!.Apellido.toString(),TextView.BufferType.EDITABLE)
        ViewEmail.setText(UsuerInstance!!.Email.toString(),TextView.BufferType.EDITABLE)
        ViewPhone.setText(UsuerInstance!!.Telefono.toString(),TextView.BufferType.EDITABLE)

        Glide.with(applicationContext)
            .load(UsuerInstance!!.UriImagen.toString().toUri())
            .fitCenter()
            .centerCrop()
            .into(ImageViewProfile)

        checkboxName.setOnCheckedChangeListener { compoundButton, b ->
            ViewName.isEnabled = b
        }
        checkboxApellido.setOnCheckedChangeListener { compoundButton, b ->
            ViewLastname.isEnabled = b
        }
        checkboxEmail.setOnCheckedChangeListener { compoundButton, b ->
            ViewEmail.isEnabled = b
        }
        checkboxTelefono.setOnCheckedChangeListener { compoundButton, b ->
            ViewPhone.isEnabled = b
        }
        ButtonEditarFoto.setOnClickListener { v->
            val intent = Intent(Intent.ACTION_PICK)
            Log.d("action pick", Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, ProfileFragment.GALERY_INTENT)
        }
    }
    override fun onStart() {
        super.onStart()

    }
    fun SaveUriImagenInFireBase(uri:Uri?,userId: String){
        Toast.makeText(applicationContext,"2",Toast.LENGTH_SHORT).show()
        InstanceFirebaseToSaveImage.reference.child("User").child(userId).child("UrlImagen").setValue(uri.toString())
    }
    fun EditFireBaseUserData(view: View){
        val referenciaDatabase = FirebaseDatabase.getInstance().reference.child("User").child(UsuerInstance?.ID.toString())
        if(checkboxName.isChecked){
            referenciaDatabase.child("Nombre").setValue(ViewName.text.toString())
            FirebaseConexion.UpdateValue(ViewName.text.toString(),"Nombre")
        }
        if(checkboxApellido.isChecked){
            referenciaDatabase.child("Apellido").setValue(ViewLastname.text.toString())
            FirebaseConexion.UpdateValue(ViewLastname.text.toString(),"Nombre")
        }
        if(checkboxEmail.isChecked){
            referenciaDatabase.child("E-mail").setValue(ViewEmail.text.toString())
            FirebaseConexion.UpdateValue(ViewEmail.text.toString(),"Nombre")
        }
        if(checkboxTelefono.isChecked){
            referenciaDatabase.child("Telefono").setValue(ViewPhone.text.toString())
            FirebaseConexion.UpdateValue(ViewPhone.text.toString(),"Nombre")
        }
        Toast.makeText(applicationContext,"Los datos fueron acutalizados correctamente",Toast.LENGTH_SHORT).show()
        finish()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Auth = FirebaseAuth.getInstance()
        val user = Auth.currentUser!!
        val userID = user.uid
        Log.d("USERID", userID)
        var ImagenName: String? = null
        val uri: Uri? = data?.data
        if (requestCode == ProfileFragment.GALERY_INTENT && resultCode == Activity.RESULT_OK) {
            ImagenName = "FotoPerfil"
            var filePath: StorageReference =
                storageRef.reference.child("fotosUsuario/" + userID + "/" + ImagenName)
                    .child("FotoPerfil.jpg")
            if (uri != null) {
                filePath.putFile(uri).addOnSuccessListener { t->
                    var downloadURL = t.metadata?.reference?.downloadUrl
                    downloadURL?.addOnCompleteListener { task->
                        var Uri = downloadURL?.result
                        SaveUriImagenInFireBase(Uri,userID)
                        if(task.isComplete && task.isSuccessful){
                            Glide.with(applicationContext)
                                .load(Uri)
                                .fitCenter()
                                .centerCrop()
                                .into(ImageViewProfile)
                            Toast.makeText(applicationContext, "Se hizo exitosamente ", Toast.LENGTH_LONG)
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




