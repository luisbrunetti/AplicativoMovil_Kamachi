package com.example.sw2.Clases

import android.net.Uri
import android.widget.ImageView

class ServicioListView(
    key:String, NT:String = "", DIST:String, Img:String,
    ImgView: ImageView?, tel: String, cali: String, Uri: Uri?, emp: Boolean?
){
    var KeyFireBase:String? = null
    var Distrito:String? = null
    var NombreTrabaj:String? = null
    var Imagen:String? = null
    var ImagenView:ImageView?=null
    var Telefono:String? = null
    var Calificacion:String? = null
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