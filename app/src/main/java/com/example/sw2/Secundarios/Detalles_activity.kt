package com.example.sw2.Secundarios

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sw2.Clases.Afiliado
import com.example.sw2.Clases.ServicioListView
import com.example.sw2.R
import com.example.sw2.interfaces.translate_fragment
import com.example.sw2.pago.PagoActivity
import java.io.Serializable

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
    //xml
    private var nombreservicio:String ? = null
    // listener
    var costo: String ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_activity)

        ImageViewFoto = findViewById(R.id.imagenViewFotoDetalles)
        tv_empresa = findViewById(R.id.textViewEmpresaCargo_Detalle)
        tv_emailservicio = findViewById(R.id.textViewEmail_Detalles)
        tv_telefonoservicio = findViewById(R.id.textViewTelefono_Detalle)
        tv_distritoservicio = findViewById(R.id.textViewDistro_Detalle)
        tv_tipopersona = findViewById(R.id.texttipopersona)
        tv_descripcion = findViewById(R.id.textViewdescripcion_detalles)
        tv_costoservicio = findViewById(R.id.tv_costo_detalles)
        tv_categoriaservicio=  findViewById(R.id.textViewCategoriaServicio_Detalle)
        rating = findViewById(R.id.ratingBar)
        var toolbar:Toolbar = findViewById(R.id.toolbar_Detalles)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        ////////////////////////////////////////////////////////

        //////
        val uri = intent.getStringExtra("uri")
        val nombretrabajo = intent.getStringExtra("nombretrabajo")
        val distrito = intent.getStringExtra("distrito")
        val email = intent.getStringExtra("email")
        val tipopersona = intent.getStringExtra("tipopersona")
        val calificacion = intent.getStringExtra("calificacion")
        costo = intent.getStringExtra("costo")
        val categoria = intent.getStringExtra("categoria")
        val telefono = intent.getStringExtra("telefono")
        val descripcion = intent.getStringExtra("descripcion")
        val duracion = intent.getStringExtra("duracion")
        val empresa = intent.getStringExtra("empresa")
        ////////////////////////////////////////////////////////////
        toolbar.title= nombretrabajo
        ////////////////////////////////////////////////////////////////////////////
        Glide.with(applicationContext)
            .load(uri).fitCenter().centerCrop().apply(RequestOptions.overrideOf(160,180)).into(ImageViewFoto)
        tv_emailservicio?.setText(email)
        tv_telefonoservicio?.text = telefono
        tv_distritoservicio?.setText(distrito)
        tv_tipopersona?.text = tipopersona
        tv_costoservicio?.text = "S/. "+costo

        tv_categoriaservicio?.text = categoria

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        //////////////////////////////////////////////////////////////////////
        menuInflater.inflate(R.menu.menu_detalles,menu)
        return super.onCreateOptionsMenu(menu)
    }
    private fun inicializatePago(){
        val intent = Intent(this,PagoActivity::class.java)
        intent.putExtra("cost",costo)
        startActivity(intent)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_contratar -> inicializatePago()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun obtenerparametros(){


    }

}
