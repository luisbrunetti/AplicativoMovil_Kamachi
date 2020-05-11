package com.example.sw2.Secundarios

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sw2.R

class Detalles_activity : AppCompatActivity() {
    private lateinit var ImageViewFoto : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detalles_activity)
        var a = intent.getStringExtra("pos").toInt()
        ImageViewFoto = findViewById(R.id.imagenViewFotoDetalles)
        var uri: Uri? = intent.getStringExtra("foto").toUri()
        var NombreTrabajo:String = intent.getStringExtra("NombreTrabajo")
        var Distrito:String = intent.getStringExtra("distrito")

        //Creando Toolbar de detalles
        var toolbar:Toolbar = findViewById(R.id.toolbar_Detalles)
        toolbar.title = NombreTrabajo
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Glide.with(applicationContext)
            .load(uri).fitCenter().centerCrop().apply(RequestOptions.overrideOf(160,180)).into(ImageViewFoto)
        Toast.makeText(applicationContext,a.toString(),Toast.LENGTH_SHORT).show()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_detalles,menu)
        return super.onCreateOptionsMenu(menu)
    }
}
