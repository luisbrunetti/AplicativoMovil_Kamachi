package com.example.sw2.patrones_diseño.factory_Register

import android.content.Intent

class ClaseInstaciarActivityRegistro {
    fun SelectRegister(TipoRegistro : String ){
        when(TipoRegistro){
            "Cliente"-> CrearInstancia()
            "Afiliado"-> CrearInstancia()

        }
    }
    fun CrearInstancia(ins : InterfaceConexionRegistro){
        var a = ins.
    }
}