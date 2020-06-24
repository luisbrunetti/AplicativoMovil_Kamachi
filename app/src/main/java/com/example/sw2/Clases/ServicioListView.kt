package com.example.sw2.Clases

import android.net.Uri
import android.widget.ImageView
import java.io.Serializable

data class ServicioListView(val empresa:String?,val categoria_servicio:String?,
                            val Email_servicio:String?,
                            val ID_Ailiado:String?,
                            val Tipo_persona:String?,
                            var Uri:String?,
                            val cost_service:String?,
                            val description:String?,
                            val distrito:String?,
                            val duracion:String?,
                            val key : String?,
                            val nombreTrabajo:String?,
                            val telefono:String?,
                            val calificacion : String?) : Serializable
