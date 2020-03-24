package com.example.sw2.framents

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.sw2.R
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AfiliacionFragment: Fragment() {

    private lateinit var storageRef : StorageReference
    private lateinit var botonSubir: Button
    private lateinit var vista : View
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


        botonSubir.setOnClickListener(View.OnClickListener { v ->
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, GALERY_INTENT)
        })
        return vista
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


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
                var filePath:StorageReference = storageRef.child("fotosUsuario").child(uri?.lastPathSegment.toString())
            if (uri != null) {
                filePath.putFile(uri).addOnSuccessListener(taskSnapShot {



                } )
            }
        }
    }
}