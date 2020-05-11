package com.example.sw2.Clases

import android.media.Image
import android.net.Uri
import android.widget.ImageView

class ServicioListViewTesteo(key:String="",NombreTrabaj:String = "",Distrito:String = "",
                             Imagen:String = "",ImagenView: ImageView? = null,
                             Telefono: String?,Calificacion:Int = -1,UriImagen: Uri? = null){
    var Distrito:String? = null
    var NombreTrabaj:String? = null
    var Imagen:String? = null
    var ImagenView: ImageView?=null
    var Telefono:String? = null
    var Calificacion:Int = -1
    var UriImagen: Uri? = null
    var key: String? = null
    init{
        this.Distrito = Distrito
        this.NombreTrabaj = NombreTrabaj
        this.Imagen = Imagen
        this.ImagenView = ImagenView
        this.UriImagen = UriImagen
        this.key = key
        this.Telefono = Telefono
        this.Calificacion = Calificacion
    }
}