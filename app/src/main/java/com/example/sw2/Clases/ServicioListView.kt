package com.example.sw2.Clases

import android.net.Uri
import android.widget.ImageView

class ServicioListView(var key:String? = null,var NT:String = "",var DIST:String = "" ,var Img:String = "",
                       var ImgView:ImageView? = null,var tel: String? ,var cali:Int = -1,var Uri: Uri? = null,var emp : Boolean? = null){
    var KeyFireBase:String? = null
    var Distrito:String? = null
    var NombreTrabaj:String? = null
    var Imagen:String? = null
    var ImagenView:ImageView?=null
    var Telefono:String? = null
    var Calificacion:Int = -1
    var Empresa:Boolean? = false
    var UriImagen:Uri? = null
    init{
        this.Distrito = DIST
        this.NombreTrabaj = NT
        this.Imagen = Img
        this.KeyFireBase = key
        this.ImagenView = ImgView
        this.UriImagen = Uri
        this.Telefono = tel
        this.Empresa = emp
        this.Calificacion = cali
    }
}