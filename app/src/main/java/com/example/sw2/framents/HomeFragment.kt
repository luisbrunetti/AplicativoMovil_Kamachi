package com.example.sw2.framents


import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.example.sw2.Clases.ReclyceViewAdapter
import com.example.sw2.Clases.ServicioListView
import com.example.sw2.R
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class HomeFragment: Fragment() {
    private lateinit var myRecyclyview : RecyclerView
    private lateinit var intentRecive : String
    //Firebaser variables
    private lateinit var storageRef: StorageReference
    private lateinit var userFirebase:FirebaseUser
    private lateinit var auth: FirebaseAuth
    lateinit var v:View
    lateinit var lstServicios : ArrayList<ServicioListView>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_home,container,false)
        myRecyclyview = v.findViewById(R.id.recycleview_homefragment)

        //Inicialización Firebaase//
        storageRef = FirebaseStorage.getInstance().reference
        auth = FirebaseAuth.getInstance()
        userFirebase = auth.currentUser!!
        ///////////////////////////////////

        retrieveDataFromFireBase()
        return v
        /**
         * public View onCreateView(LayoutInflater inflater,
        * ViewGroup container,
        * Bundle savedInstanceState) {
        * View view = inflater.inflate(R.layout.testclassfragment, container, false);
        * ImageView imageView = (ImageView) view.findViewById(R.id.my_image);
        * return view;
        * }*/
        //retrieveDataFromFireBase()
    }
    private fun retrieveDataFromFireBase(){
        val reff  = FirebaseDatabase.getInstance().reference.child(    "Servicios")
        Log.d("datos",reff.toString())
        val postListener = object :  ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(p0: DataSnapshot) {
                for(h in p0.children){
                    Log.d("#key ", h.key.toString())
                    var a : ServicioListView? = h.getValue(ServicioListView::class.java)
                    if (a != null) {
                        /*Toast.makeText(context,a.NombreTrabaj ,Toast.LENGTH_LONG).show()
                        Toast.makeText(context,a.Distrito,Toast.LENGTH_LONG).show()
                        Toast.makeText(context,a.Imagen,Toast.LENGTH_LONG).show()*/

                        lstServicios.add(a)
                        DownloadImagsToRecycleView(h,a)


                    }else{
                        Toast.makeText(context,"Error al ingresar alguno de los datos",Toast.LENGTH_LONG).show()
                    }
                            }
            }
        }
        reff.addValueEventListener(postListener)
    }
    private fun DownloadImagsToRecycleView(h:DataSnapshot,a:ServicioListView?) {
        var key : String = h.key.toString()
        var drawable:ImageView? = null
        drawable?.setImageResource(R.drawable.backwithborder)
        Log.d("Key de h -> ",storageRef.child("ImagenServicios/"+key).
            child("Fotoservicio.jpg").downloadUrl.toString())
        var filepath: Task<Uri> =storageRef.child("ImagenServicios/"+key).
                child("Fotoservicio.jpg").downloadUrl.addOnSuccessListener(OnSuccessListener {
            if (it != null) {
                    var  recycleAdapter = ReclyceViewAdapter(requireActivity(),lstServicios,it)
                    a?.ImagenView = drawable
                    myRecyclyview.layoutManager = LinearLayoutManager(context)
                    myRecyclyview.adapter = recycleAdapter
                }


        })


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lstServicios = ArrayList()


        /**
         *  var urlDescarga : Task<Uri>? = it.metadata?.reference?.downloadUrl
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
         */

    }
}
