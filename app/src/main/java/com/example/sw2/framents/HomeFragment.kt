package com.example.sw2.framents


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
import com.example.sw2.Clases.ReclyceViewAdapter
import com.example.sw2.Clases.ServicioListView
import com.example.sw2.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
class HomeFragment: Fragment() {
    private lateinit var myRecyclyview : RecyclerView
    private lateinit var intentRecive : String
    lateinit var v:View
    lateinit var lstServicios : ArrayList<ServicioListView>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_home,container,false)
        myRecyclyview = v.findViewById(R.id.recycleview_homefragment)
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
                //Toast.makeText(context,p0.child("distrito").value.toString(),Toast.LENGTH_LONG).show()
                for(h in p0.children){
                    var a : ServicioListView? = h.getValue(ServicioListView::class.java)
                    if (a != null) {
                        /*Toast.makeText(context,a.NombreTrabaj ,Toast.LENGTH_LONG).show()
                        Toast.makeText(context,a.Distrito,Toast.LENGTH_LONG).show()
                        Toast.makeText(context,a.Imagen,Toast.LENGTH_LONG).show()*/
                        lstServicios.add(a)
                        var  recycleAdapter = ReclyceViewAdapter(requireActivity(),lstServicios)
                        myRecyclyview.layoutManager = LinearLayoutManager(context)
                        myRecyclyview.adapter = recycleAdapter
                    }else{
                        Toast.makeText(context,"Error al ingresar alguno de los datos",Toast.LENGTH_LONG).show()
                    }
                            }
            }
        }
        reff.addValueEventListener(postListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lstServicios = ArrayList()

    }
}
