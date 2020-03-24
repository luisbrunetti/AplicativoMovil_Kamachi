package com.example.sw2.Clases

class ServicioListView(var NT:String = "",var DIST:String = "" ,var Img:String = ""){
    var Distrito:String? = null
    var NombreTrabaj:String? = null
    var Imagen:String? = null
    init{
        this.Distrito = DIST
        this.NombreTrabaj = NT
        this.Imagen = Img
    }
}