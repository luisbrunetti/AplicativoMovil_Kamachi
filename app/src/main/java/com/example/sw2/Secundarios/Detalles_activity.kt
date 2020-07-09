package com.example.sw2.Secundarios

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sw2.Clases.Afiliado
import com.example.sw2.Clases.Contratos
import com.example.sw2.Clases.ServicioListView
import com.example.sw2.MainActivity
import com.example.sw2.R
import com.example.sw2.framents.HomeFragment
import com.example.sw2.interfaces.translate_fragment
import com.example.sw2.pago.PagoActivity
import com.example.sw2.patrones_diseño.RecycleViewHome.ReclyceViewAdapter_ServiciosHome
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_detalles_activity.*
import org.w3c.dom.Text
import java.io.Serializable

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class Detalles_activity : AppCompatActivity() ,Serializable{
    private lateinit var ImageViewFoto : ImageView
    private var tv_empresa:TextView? = null
    private var tv_emailservicio:TextView? = null
    private var tv_categoriaservicio:TextView ? =null
    private var tv_telefonoservicio:TextView? = null
    private var tv_distritoservicio:TextView? = null
    private var tv_tipopersona:TextView? = null
    private var tv_costoservicio:TextView? = null
    private var tv_descripcion:TextView? = null
    private var rating:RatingBar? = null
    private var toolbar:Toolbar? = null
    //xml
    private var result : TextView? = null
    private var nombreservicio:String ? = null
    // listener
    var costo: String ? = null
    var key:String? = null
    private lateinit var service:ServicioListView
    private lateinit var servicioContrato: Contratos

    override fun onResume() {
        super.onResume()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_activity)
        result = findViewById(R.id.tviresult)
        ImageViewFoto = findViewById(R.id.imagenViewFotoDetalles)
        tv_empresa = findViewById(R.id.textViewEmpresaCargo_Detalle)
        tv_emailservicio = findViewById(R.id.textViewEmail_Detalles)
        tv_telefonoservicio = findViewById(R.id.textViewTelefono_Detalle)
        tv_distritoservicio = findViewById(R.id.textViewDistro_Detalle)
        tv_tipopersona = findViewById(R.id.texttipopersona)
        tv_descripcion = findViewById(R.id.textViewdescripcion_detalles)
        tv_costoservicio = findViewById(R.id.tv_costo_detalles)
        tv_categoriaservicio=  findViewById(R.id.textViewCategoriaServicio_Detalle)
        toolbar = findViewById(R.id.toolbar_Detalles)
        rating = findViewById(R.id.ratingBar)
        nombreservicio = intent.getStringExtra("nombretrabajo")

        ////////////////////////////////////////////////////////
        if (intent.getStringExtra("xml").toInt() == 1){
            ViewDetails()
        }else{
            val a = intent.extras!!.getSerializable("serviceContratado") as Contratos
            val FirebaseReference = FirebaseDatabase.getInstance().reference
            val query = FirebaseReference.child("Servicios").orderByChild("key").equalTo(a.ID_servicio)
            query.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    for (h in p0.children) {
                        Log.d("costo",h.child("cost_service").value.toString())
                        Glide.with(applicationContext)
                            .load(h.child("Uri").value.toString()).fitCenter().centerCrop().apply(RequestOptions.overrideOf(160,180)).into(ImageViewFoto)
                        tv_emailservicio?.setText(h.child("Email_servicio").value.toString())
                        tv_telefonoservicio?.text = h.child("telefono").value.toString()
                        tv_distritoservicio?.setText(h.child("distrito").value.toString())
                        tv_tipopersona?.text = h.child("Tipo_persona").value.toString()
                        tv_costoservicio?.text = "S/. "+h.child("cost_service").value.toString()
                        tv_categoriaservicio?.text = h.child("Categoria_servicio").value.toString()
                    }
                }
            })
            toolbar!!.title= a.Nombre_servicio_contratado
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            tv_estado_detalles.text = "En " + a.Estado.toString()
            tv_estado_detalles.visibility = View.VISIBLE
            but_details_confirmarservicio.visibility = View.VISIBLE

            but_details_servicioterminado.setOnClickListener {
                rating()
            }
        }
        /////

    }
    private fun rating(){
        GlobalUtils.showdialog(this, object : DialogCallback {
            override fun callback(rating: Int) {
                Toast.makeText(applicationContext,
                    "Se califico al servicio con $rating estrellas",Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun ViewDetails(){
        val uri = intent.getStringExtra("uri")
        val distrito = intent.getStringExtra("distrito")
        val email = intent.getStringExtra("email")
        val tipopersona = intent.getStringExtra("tipopersona")
        val calificacion = intent.getStringExtra("calificacion")
        costo = intent.getStringExtra("costo")
        key = intent.getStringExtra("key")
        val id_afiliado = intent.getStringExtra("id_afiliado")
        val categoria = intent.getStringExtra("categoria")
        val telefono = intent.getStringExtra("telefono")
        val descripcion = intent.getStringExtra("descripcion")
        val duracion = intent.getStringExtra("duracion")
        val empresa = intent.getStringExtra("nombreempresa")
        Log.d("empresa",empresa)
        service = ServicioListView(empresa,categoria,email,id_afiliado,tipopersona,uri,costo,descripcion,distrito,duracion,key,nombreservicio,telefono,calificacion)
        ////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////
        Glide.with(applicationContext)
            .load(uri).fitCenter().centerCrop().apply(RequestOptions.overrideOf(160,180)).into(ImageViewFoto)
        tv_emailservicio?.setText(email)
        tv_telefonoservicio?.text = telefono
        tv_distritoservicio?.setText(distrito)
        tv_tipopersona?.text = tipopersona
        tv_costoservicio?.text = "S/. "+costo
        tv_empresa?.text = empresa
        tv_categoriaservicio?.text = categoria

        toolbar!!.title= nombreservicio
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalles,menu)
        if (intent.getStringExtra("xml").toInt() == 1){

        }else{
            val item = menu?.findItem(R.id.item_contratar)
            item!!.isVisible = false
        }

        return super.onCreateOptionsMenu(menu)
    }
    private fun inicializatePago(){
        val bundle = Bundle()
        bundle.putSerializable("service",service)
        val intent = Intent(this,PagoActivity::class.java)
        intent.putExtras(bundle)
        intent.putExtra("cost",costo)
        intent.putExtra("key",key)
        startActivity(intent)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_contratar -> inicializatePago()
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }


}
