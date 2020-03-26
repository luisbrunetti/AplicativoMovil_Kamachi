package com.example.sw2.framents

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.example.sw2.MainActivity
import com.example.sw2.R
import com.example.sw2.RegistrarActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AfiliacionFragment: Fragment() {

    private lateinit var storageRef : StorageReference
    private lateinit var botonSubir: Button
    private lateinit var vista : View
    // Varaible para la autentificación en Firebase
    private lateinit var authFirbase:FirebaseAuth
    ///Testeando
    private lateinit var imagenDownload : ImageView;

    private lateinit var progressbar: ProgressBar;
    companion object{
        private val IMAGE_PICK_CODE :Int = 1000
        private val GALERY_INTENT :Int = 1

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vista = inflater.inflate(R.layout.fragment_afiliacion,container,false)
        storageRef = FirebaseStorage.getInstance().reference
        botonSubir = vista.findViewById(R.id.buttonsubirImage)

        imagenDownload = vista.findViewById(R.id.imagen_Descargada)

        //Creando Posgreess Bar con codigo
        progressbar = vista.findViewById(R.id.progressBar_afiliaciónFragment);


        botonSubir.setOnClickListener(View.OnClickListener { v ->
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, GALERY_INTENT)
        })
        return vista
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Probando el "currenmt user "
        authFirbase = FirebaseAuth.getInstance()
        val user :FirebaseUser? = authFirbase.currentUser
        val userID: String? = user?.uid
        var ImageName:String? = null

        Toast.makeText(context,user.toString() +" ----------"+userID,Toast.LENGTH_LONG).show()

        if(requestCode == GALERY_INTENT && resultCode == RESULT_OK){
            /**
             * ref.putFile(file).addOnSuccessListener (
            object : OnSuccessListener<UploadTask.TaskSnapshot> {
            override fun onSuccess(taskSnapshot: UploadTask.TaskSnapshot?) {
            val url = taskSnapshot?.downloadUrl
            Log.d("FirebaseManager", "Upload Successful")
            downloadCallback.callback(url.toString())
            }
            }
            )
             */
            val uri :Uri? = data?.data
            ImageName = "FotoPerfil"
            var filePath:StorageReference = storageRef.child("fotosUsuario/"+userID+"/"+ImageName).child("FotoPerfil.jpg")
            Log.d("FilePath - >",uri?.lastPathSegment.toString())
            if (uri != null) {
                filePath.putFile(uri).addOnSuccessListener{
                    progressbar.visibility = View.VISIBLE
                    // Obtenie
                    // ndo el URL de descarga de las imagenes
                    var urlDescarga : Task<Uri>? = it.metadata?.reference?.downloadUrl
                    //Agregando un Listener , ya que (isComplete por si solo no es suficieinte)
                    urlDescarga?.addOnCompleteListener { task: Task<Uri> ->
                        var Uri: Uri? = urlDescarga?.result
                        Log.d("urlDescarga",Uri.toString())
                        if (urlDescarga != null) {
                            if (task.isComplete && task.isSuccessful) {
                                Glide.with(vista)
                                    .load(Uri)
                                    .fitCenter()
                                    .centerCrop()
                                    .into(imagenDownload)
                                Toast.makeText(context, "Se hizo exitosamente ", Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                    }
                }.addOnFailureListener { exception ->
                    progressbar.visibility = View.GONE
                    Toast.makeText(context,"Ocurrio el error "+exception+" al subir la iamgen",Toast.LENGTH_LONG).show()
                }
                progressbar.visibility = View.GONE
                /**
                 * .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                });
                 */
            }
        }
    }/*
    private fun Upload_Imanges_Test(){
        var reff : DatabaseReference = FirebaseDatabase.getInstance().reference.child("Servicios")
        ,
        var R : RegistrarActivity = RegistrarActivity()
        for(h in R.Array){
            var key : = h.ke
        }
        var filepathImageUpload:StorageReference =storageRef.child("ImagenServicios"/)
    }*/
}