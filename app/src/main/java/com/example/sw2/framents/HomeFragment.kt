package com.example.sw2.framents

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.sw2.Clases.ServicioListView
import com.example.sw2.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment: Fragment() {
    private var ListView: ListView? = null
    private var vistalayout = view
    private lateinit var intentRecive : String
    var Lista: ArrayList<ServicioListView>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
/*
        ListView=
            view?.findViewById(R.id.listViewfragment_home)  // Se tiene que crear una variable vista para poder llamar a los componentes del fragmento
        /**
         * public View onCreateView(LayoutInflater inflater,
        * ViewGroup container,
        * Bundle savedInstanceState) {
        * View view = inflater.inflate(R.layout.testclassfragment, container, false);
        * ImageView imageView = (ImageView) view.findViewById(R.id.my_image);
        * return view;
        * }
         */*/
        retrieveDataFromFireBase()

        return inflater.inflate(R.layout.fragment_home,container,false)




    }

    fun retrieveDataFromFireBase(){
        val reff  = FirebaseDatabase.getInstance().reference.child(    "Servicios")
        Log.d("datos",reff.toString())
        val postListener = object :  ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(p0: DataSnapshot) {
                for(h in p0.children){
                    var prueba = h.getValue(ServicioListView::class.java)
                    Lista?.add(prueba!!)
                    Log.d("Lista",Lista.toString())
                    Log.d("valores,prueba",prueba.toString())
                }
                Log.d("Información del Data Snapshot",p0.toString())
            }
        }
        reff.addValueEventListener(postListener)

    }



}