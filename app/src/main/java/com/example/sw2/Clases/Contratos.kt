package com.example.sw2.Clases

import java.io.Serializable

data class Contratos(val Nombre_servicio_contratado:String,
                     val empresa: String,
                     val ID_Pago:String,
    val ID_servicio:String,
    val ID_usuario:String,
                     val ID_Afiliado:String,
    val Mount:String,
    val Estado:String,
    val Uri_Servicio_Contratado:String,
    val Duracion:String,
    val Date:String):Serializable