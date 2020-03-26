package com.example.sw2.Clases

import android.net.Uri
import android.widget.ImageView

class ServicioListView(var NT:String = "",var DIST:String = "" ,var Img:String = "",var ImgView:ImageView? = null,var tel: String? ,var cali:Int = -1, var Uri: Uri? = null){
    var Distrito:String? = null
    var NombreTrabaj:String? = null
    var Imagen:String? = null
    var ImagenView:ImageView?=null
    var Telefono:String? = null
    var Calificación:Int = -1
    var UriImagen:Uri? = null
    init{
        this.Distrito = DIST
        this.NombreTrabaj = NT
        this.Imagen = Img
        this.ImagenView = ImgView
        this.UriImagen = Uri
        this.Telefono = tel
        this.Calificación = cali
    }
}