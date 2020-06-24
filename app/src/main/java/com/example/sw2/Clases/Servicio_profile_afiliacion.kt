package com.example.sw2.Clases

import java.io.Serializable

data class Servicio_profile_afiliacion(val categoria_servicio:String,
    val Email_servicio:String,
    val ID_Ailiado:String,
    val Tipo_persona:String,
    val Uri:String,
    val cost_service:String,
    val description:String,
    val distrito:String,
    val duracion:String,
    val key : String,
    val nombreTrabajo:String,
    val telefono:String,
val calificacion:String) : Serializable