package com.example.sw2.Clases

class Usuario(
    nombre:String,
    apellido:String,
    distirto:String,
    Email:String,
    telefono:String,
    UriImagen:String,
    ID:String,
    IDServicio:String,
    afiliado: Boolean
){
    var Nombre: String?= null
    var Apellido:String?= null
    var Distrito: String? = null
    var Email:String?= null
    var Telefono: String? = null
    var UriImagen: String? = null
    var ID:String? = null
    var IDAfiliado:String? = null
    var Afiliado:Boolean?=null
    init {
        Nombre = nombre
        Apellido = apellido
        Distrito = distirto
        this.Email= Email
        this.Telefono = telefono
        this.UriImagen = UriImagen
        this.ID = ID
        this.Afiliado = afiliado
        this.IDAfiliado = IDServicio
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return super.toString()
    }
}