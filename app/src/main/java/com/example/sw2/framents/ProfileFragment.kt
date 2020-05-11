package com.example.sw2.framents

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.sw2.Clases.FirebaseConexion
import com.example.sw2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ProfileFragment: Fragment() {
    private lateinit var FirebaseConexion: FirebaseConexion
    private lateinit var InstanceFirebaseToSaveImage:FirebaseDatabase
    private lateinit var ViewName: TextView
    private lateinit var ViewLastname: TextView
    private lateinit var ViewEmail: TextView
    private lateinit var ViewPhone: TextView
    private lateinit var ImageViewProfile:ImageView
    private lateinit var Toolbar_profile:Toolbar
    private lateinit var ButtonEditPhoto: Button
    private lateinit var Auth: FirebaseAuth
    private lateinit var storageRef: FirebaseStorage
    private lateinit var v:View
    private var emailUser: String? = null

    companion object {
        val GALERY_INTENT = 1
        val IMAGE_PICK_CODE: Int = 1000
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_profile, container, false)
        storageRef = FirebaseStorage.getInstance()
        InstanceFirebaseToSaveImage = FirebaseDatabase.getInstance()
        ViewName = v.findViewById<TextView>(R.id.textView_name_profile)
        ViewLastname = v.findViewById<TextView>(R.id.textViewLastName_profile)
        ViewEmail = v.findViewById<TextView>(R.id.textViewEmail_Profile)
        ViewPhone = v.findViewById<TextView>(R.id.textViewphone_profile)
        ImageViewProfile = v.findViewById(R.id.imageView_profile)
        Toolbar_profile = v.findViewById(R.id.toolbar_profile)
        Toolbar_profile.title = "Perfil"
        return v
    }
    override fun onStart() {
        super.onStart()

    }

    override fun onPause() {
        super.onPause()
    }
    override fun onResume() {
        super.onResume()
        var uriImagen: String? = null
        FirebaseConexion = FirebaseConexion(requireContext())
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




