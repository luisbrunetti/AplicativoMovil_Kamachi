package com.example.sw2.framents

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.sw2.Clases.AdaptadorListView
import com.example.sw2.Clases.ServicioListView
import com.example.sw2.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.*

class HomeFragment: Fragment() {
    private var ListView: ListView? = null
    private var vistalayout = view
    private var adaptador: AdaptadorListView? =null
    private lateinit var intentRecive : String
    var Lista: ArrayList<ServicioListView>? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        Lista = ArrayList()
        Lista?.add(ServicioListView("Servicio Electrico" ,"SJL",0))
        Lista?.add(ServicioListView("Servicio Mecanico" ,"San Luis",1))

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
        adaptador = AdaptadorListView(activity,Lista!!)
        ListView?.adapter = adaptador
*/
        ListView?.adapter = activity?.let {
            ArrayAdapter(
                it,R.layout.support_simple_spinner_dropdown_item,
                Lista!!
            )
        }
        //retrieveDataFromFireBase()
        return inflater.inflate(R.layout.fragment_home,container,false)
    }
    fun retrieveDataFromFireBase(){
        val reff  = FirebaseDatabase.getInstance().getReference().child(    "Servicios").child("-M2zHQllD_lB0er61cMj")
        Log.d("datos",reff.toString())
        val postListener = object :  ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(p0: DataSnapshot) {
                Toast.makeText(context,p0.child("distrito").value.toString(),Toast.LENGTH_LONG).show()
            }
        }
        reff.addValueEventListener(postListener)

    }



}