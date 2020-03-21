package com.example.sw2.framents

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.sw2.Clases.AdaptadorListView
import com.example.sw2.Clases.ServicioListView
import com.example.sw2.R
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
         */
        adaptador = AdaptadorListView(activity,Lista!!)
        ListView?.adapter = adaptador

        return inflater.inflate(R.layout.fragment_home,container,false)




    }



}