package com.example.sw2.framents

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.sw2.Clases.Usuario
import com.example.sw2.R
import com.example.sw2.interfaces.toolbar_transaction
import com.example.sw2.patrones_diseño.singleton.FirebaseConexion
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.auth.User
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment: Fragment() {
    private var m_Text: String = ""
    //Interface toolbar_transaction
    var int_toolbar_trans_profilefrag: toolbar_transaction? = null
    //////////////////////////////////
    private lateinit var FirebaseConexion: FirebaseConexion
    private lateinit var InstanceFirebaseToSaveImage:FirebaseDatabase
    private var UsuarioProfileFraagment:Usuario? = null
    ////////////////////////////////////
    private lateinit var ViewName: TextView
    private lateinit var ViewLastname: TextView
    private lateinit var ViewEmail: TextView
    private lateinit var ViewPhone: TextView
    private lateinit var Toolbar_profile:Toolbar
    private lateinit var ButtonEditPhoto: Button
    //Checkbox update
    private lateinit var checkbox_nombre_profile:CheckBox
    private lateinit var checkbox_apellido_profile:CheckBox
    private lateinit var checkbox_email_profile:CheckBox
    private lateinit var checkbox_telefono_profile:CheckBox
    private var show : Boolean = false
    ////////////////////////////////
    private lateinit var Auth: FirebaseAuth
    private lateinit var storageRef: FirebaseStorage
    private lateinit var v:View
    private var emailUser: String? = null
    //Cargar Imagen
    private lateinit var ImageViewProfile:ImageView
    private var Uri: Uri? = null
    companion object {
        val GALERY_INTENT = 1
        private val IMAGE_PICK_CODE: Int = 1000
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        int_toolbar_trans_profilefrag = activity as toolbar_transaction
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_profile, container, false)
        storageRef = FirebaseStorage.getInstance()
        InstanceFirebaseToSaveImage = FirebaseDatabase.getInstance()
        FirebaseConexion =
            FirebaseConexion(
                requireContext()
            )
        UsuarioProfileFraagment = FirebaseConexion.getStoreSaved()

        ViewName = v.findViewById<TextView>(R.id.textView_name_profile)
        ViewLastname = v.findViewById<TextView>(R.id.textViewLastName_profile)
        ViewEmail = v.findViewById<TextView>(R.id.textViewEmail_Profile)
        ViewPhone = v.findViewById<TextView>(R.id.textViewphone_profile)
        ImageViewProfile = v.findViewById(R.id.imageView_profile)
        checkbox_nombre_profile = v.findViewById(R.id.checkbox_nombre_profile)
        checkbox_apellido_profile = v.findViewById(R.id.checkbox_lastname_profile)
        checkbox_email_profile= v.findViewById(R.id.checkbox_email_profile)
        checkbox_telefono_profile= v.findViewById(R.id.checkbox_phone_profile)
        ///Listener de cambio del checkbox
        checkbox_nombre_profile.setOnCheckedChangeListener{ _, b ->
            if(b){
                UpdateProfileVariables(b,"Modificación del nombre","Ingrese el nombre que desea mostrar",checkbox_nombre_profile,ViewName)
            }
        }
        checkbox_apellido_profile.setOnCheckedChangeListener{_,b ->
            if(b){
                UpdateProfileVariables(b,"Modificación del apellido","Ingrese el apellido que desea mostrar",checkbox_apellido_profile,ViewLastname)
            }
        }
        checkbox_email_profile.setOnCheckedChangeListener{_,b->
            if(b){
                UpdateProfileVariables(b,"Modificación del correo electronico","Ingrese el correo electronico que desea mostrar",checkbox_email_profile,ViewEmail)
            }
        }

        checkbox_telefono_profile.setOnCheckedChangeListener{_,b->
            if(b){
                UpdateProfileVariables(b,"Modificación del telefono","Ingrese el telefono que desea mostrar",checkbox_telefono_profile,ViewPhone)
            }
        }
        ImageViewProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            Log.d("action pick", Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, ProfileFragment.GALERY_INTENT)
        }
        setHasOptionsMenu(true)
        int_toolbar_trans_profilefrag?.change_tittle("Perfil")

        return v
    }
    fun UpdateProfileVariables(b:Boolean,title:String,message:String?,checkBox: CheckBox,tview:TextView){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title).setMessage(message)
        val input = EditText(requireContext())
        input.inputType = InputType.TYPE_CLASS_TEXT /*or InputType.TYPE_TEXT_VARIATION_PASSWORD*/
        builder.setView(input)
        builder.setPositiveButton("OK", object : DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                UpdateValueFirebase(input.text.toString(),tview)
            }
        })
        builder.setNegativeButton("Cancel") { dialog, _ ->
                checkBox.isChecked = false
                dialog.cancel()
        }
        builder.show()
    }
    fun UpdateValueFirebase(m_Text:String,tview:TextView){
        var pathString: String? = null
        when(tview){
            ViewName -> pathString = "Nombre"
            ViewLastname -> pathString = "Apellido"
            ViewEmail-> pathString = "E-mail"
            ViewPhone->pathString= "Telefono"
        }
        val referenciaDatabase = FirebaseDatabase.getInstance().reference.child("User").child(UsuarioProfileFraagment?.ID.toString())
        referenciaDatabase.child(pathString!!).setValue(m_Text)
        FirebaseConexion.UpdateValue(m_Text,pathString!!)
        Toast.makeText(requireContext(),"Se han actualizado tu "+ pathString.toLowerCase()+
            " correctamente",Toast.LENGTH_SHORT).show()
        tview.text = m_Text
    }
    override fun onResume() {
        super.onResume()
        var uriImagen: String? = null
        FirebaseConexion =
            FirebaseConexion(
                requireContext()
            )
        var ObjectUser = FirebaseConexion.getStoreSaved()
        Toast.makeText(requireContext(),ObjectUser?.Email.toString(),Toast.LENGTH_SHORT).show()
        var query: Query =
            FirebaseDatabase.getInstance().reference.child("User").orderByChild("E-mail")
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
    fun SaveUriImagenInFireBase(uri:Uri?,userId: String){
        Toast.makeText(context,"2",Toast.LENGTH_SHORT).show()
        InstanceFirebaseToSaveImage.reference.child("User").child(userId).child("UrlImagen").setValue(uri.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_profile,menu)

    }

    private fun ShowCheckBoxs(){
        if(!show){
            checkbox_nombre_profile.visibility= View.VISIBLE
            checkbox_apellido_profile.visibility = View.VISIBLE
            checkbox_email_profile.visibility = View.VISIBLE
            checkbox_telefono_profile.visibility = View.VISIBLE
            this@ProfileFragment.show = true
        }else{
            checkbox_nombre_profile.visibility= View.GONE
            checkbox_apellido_profile.visibility = View.GONE
            checkbox_email_profile.visibility = View.GONE
            checkbox_telefono_profile.visibility = View.GONE
            this@ProfileFragment.show = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> int_toolbar_trans_profilefrag?.finish_fragment() //startActivity(Intent(requireContext(), EditarPefil::class.java))
            R.id.editarperfil-> ShowCheckBoxs()
        }
        return super.onOptionsItemSelected(item)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Auth = FirebaseAuth.getInstance()
        val user = Auth.currentUser!!
        val userID = user.uid
        Log.d("USERID", userID)
        var ImagenName: String? = null
        val uri: Uri? = data?.data
        if (requestCode == GALERY_INTENT && resultCode == RESULT_OK) {
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
                                Glide.with(requireContext())
                                    .load(Uri)
                                    .fitCenter()
                                    .centerCrop()
                                    .into(ImageViewProfile)
                                Toast.makeText(context, "Se hizo exitosamente ", Toast.LENGTH_LONG)
                                    .show()
                            }else{
                                Toast.makeText(context, "Ocurrio al descargar la imagen", Toast.LENGTH_LONG)
                                    .show()
                            }
                        }

                    }
                }
            }
        }

}




