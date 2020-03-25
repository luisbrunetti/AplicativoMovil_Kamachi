package com.example.sw2.Clases

import android.widget.ImageView

class ServicioListView(var NT:String = "",var DIST:String = "" ,var Img:String = "",var ImgView:ImageView? = null){
    var Distrito:String? = null
    var NombreTrabaj:String? = null
    var Imagen:String? = null
    var ImagenView:ImageView?=null
    init{
        this.Distrito = DIST
        this.NombreTrabaj = NT
        this.Imagen = Img
        this.ImagenView = ImgView
    }
}