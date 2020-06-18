package com.example.sw2.framents


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sw2.Clases.ServicioListView
import com.example.sw2.MainActivity
import com.example.sw2.R
import com.example.sw2.Secundarios.Detalles_activity
import com.example.sw2.interfaces.toolbar_transaction
import com.example.sw2.patrones_diseño.factory.IntefaceClickListeer
import com.example.sw2.patrones_diseño.factory.ReclyceViewAdapter_ServiciosHome
import com.example.sw2.patrones_diseño.singleton.FirebaseConexion
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() ,
    IntefaceClickListeer {
    private lateinit var myRecyclyview: RecyclerView
    //variables interface
    private var int_toolbar_transaction: toolbar_transaction? = null
    //Firebaser variables
    private lateinit var storageRef: StorageReference
    private lateinit var userFirebase: FirebaseUser
    private lateinit var auth: FirebaseAuth
    private lateinit var v: View
    private lateinit var lstServicios: ArrayList<ServicioListView>
    private lateinit var lstServiciosCopy: ArrayList<ServicioListView>
    private lateinit var FirebaseConexion: FirebaseConexion

    companion object {
        private lateinit var recycleAdapterServiciosHome: ReclyceViewAdapter_ServiciosHome

    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        int_toolbar_transaction =  activity as toolbar_transaction
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Botton Navigation view
        //Obteniendo vista del fragment
        v = inflater.inflate(R.layout.fragment_home, container, false)


        //Obteniendo la dirrecion de toolbar
        setHasOptionsMenu(true)
        //Creando acitivity para que reconozca el toolbar
        int_toolbar_transaction?.change_tittle("Servicios diponibles")

        //Obteniendo el email de el intent
        myRecyclyview = v.findViewById(R.id.recycleview_homefragment)

        //Inicialización Firebaase//
        storageRef = FirebaseStorage.getInstance().reference
        auth = FirebaseAuth.getInstance()
        userFirebase = auth.currentUser!!
        return v

    }

    private fun retrieveDataFromFireBase() {
        val reff = FirebaseDatabase.getInstance().reference.child("Servicios")
        ///Armando query
        val postListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (h in p0.children) {
                    val a = ServicioListView(
                        "",
                        h.child("nombreTrabaj").value.toString(),
                        h.child("distrito").value.toString(),
                        h.child("Imagen").value.toString(),
                        null,
                        h.child("telefono").value.toString(),
                        h.child("calificacion").value.toString().toInt(),
                        null
                    )
                    // var a : ServicioListView? = h.getValue(ServicioListView::class.java)
                    DownloadImagsToRecycleView(h, a).addOnCompleteListener { task ->
                        if (task.isComplete) {
                            recycleAdapterServiciosHome =
                                ReclyceViewAdapter_ServiciosHome(
                                    requireActivity(),
                                    lstServicios,
                                    this@HomeFragment
                                )
                            myRecyclyview.layoutManager = LinearLayoutManager(context)
                            myRecyclyview.adapter = recycleAdapterServiciosHome
                            MainActivity.bottomNav?.menu?.findItem(R.id.nav_home)?.isEnabled =
                                true
                        }
                    }
                }
            }
        }
        reff.addValueEventListener(postListener)
    }

    private fun filtrar(str: String?) {
        lstServicios.clear()
        if (str?.isEmpty()!!) {
            lstServicios = ArrayList(lstServiciosCopy)
            return
        }
        val busqueda = str.toLowerCase(Locale.ROOT)
        for (p in lstServiciosCopy) {
            if (p.NombreTrabaj?.toLowerCase(Locale.ROOT)?.contains(busqueda)!!) {
                lstServicios.add(p)
            }
        }
    }

    private fun DownloadImagsToRecycleView(h: DataSnapshot, a: ServicioListView?): Task<Uri> {
        val key: String = h.key.toString()
        val filepath: Task<Uri> = storageRef.child("ImagenServicios/$key")
            .child("Fotoservicio.png").downloadUrl.addOnSuccessListener {
                if (it != null) {
                    a?.UriImagen = it
                    if (a != null) {
                        lstServicios.add(a)
                        lstServiciosCopy = ArrayList(lstServicios)
                    }
                }
            }
        return filepath
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.navbar_navigation, menu)
        val searchview =
            SearchView((context as MainActivity).supportActionBar?.themedContext ?: context)
        menu.findItem(R.id.app_bar_search).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchview
        }
        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                /*searchview.setQuery("",false)
                menu.findItem(R.id.app_bar_search).collapseActionVie3w()*/
                filtrar(newText?.trim())
                recycleAdapterServiciosHome =
                    ReclyceViewAdapter_ServiciosHome(
                        requireActivity(),
                        lstServicios,
                        this@HomeFragment
                    )
                myRecyclyview.layoutManager = LinearLayoutManager(context)
                myRecyclyview.adapter = recycleAdapterServiciosHome

                return true
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            android.R.id.home -> int_toolbar_transaction?.finish_fragment()
        }

        return super.onOptionsItemSelected(item)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        lstServicios = ArrayList()
        FirebaseConexion =
            FirebaseConexion((requireContext()))
        retrieveDataFromFireBase()
    }

    override fun onClickListener(pos: Int) {
        Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show()
        val intent = Intent(
            activity,
            Detalles_activity::class.java
        )
        intent.putExtra("pos", pos.toString())
        intent.putExtra("foto", lstServicios[pos].UriImagen.toString())
        intent.putExtra("NombreTrabajo", lstServicios[pos].NombreTrabaj.toString())
        intent.putExtra("distrito", lstServicios[pos].Distrito.toString())
        startActivity(intent)
    }

}
