package com.example.sw2.Clases

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class FirebaseConexion(context : Context){
    var userLocalDataBase : SharedPreferences? = null
    private val SP_NAME = "userdetails"
    companion object fun InstanceFireBaseConexion(context:Context): FirebaseConexion {
            return FirebaseConexion(context)
        }

        init{
        userLocalDataBase = context.getSharedPreferences(SP_NAME,0)
    }

    fun StoreUserDate(user:Usuario?){
        var spEditor =userLocalDataBase?.edit()
        spEditor?.putString("email", user?.Email!!)
        spEditor?.putString("Nombre",user?.Nombre)
        spEditor?.putString("Apellido",user?.Apellido)
        spEditor?.putString("Distrito",user?.Distrito)
        spEditor?.putString("UrlImagen",user?.UriImagen)
        spEditor?.putString("Telefono",user?.Telefono)
        spEditor?.putString("ID",user?.ID)
        spEditor?.putString("IDAfiliado",user?.IDAfiliado)
        spEditor?.putString("Afiliado",user?.Afiliado.toString())
        Log.d("Prueba de Fi", user?.Email)
        spEditor?.apply()
    }
    fun getStoreSaved(): Usuario? {
        var Email= userLocalDataBase?.getString("email","")
        var Nombre = userLocalDataBase?.getString("Nombre","")
        var Apellido = userLocalDataBase?.getString("Apellido","")
        var Distrito = userLocalDataBase?.getString("Distrito","")
        var UriImagen = userLocalDataBase?.getString("UrlImagen","")
        var Telefono = userLocalDataBase?.getString("Telefono","")
        var ID: String? = userLocalDataBase?.getString("ID","")
        var IDAfiliado:String? = userLocalDataBase?.getString("IDAfiliado","")
        var Afiliado: String? = userLocalDataBase?.getString("Afiliado","")
        return Usuario(Nombre!!,Apellido!!,Distrito!!,Email!!,Telefono!!,UriImagen!!,ID!!,IDAfiliado!!,Afiliado.toString().toBoolean())
    }
    fun UpdateValue(value:String,update:String){
        var spUpdate = userLocalDataBase?.edit()
        spUpdate?.putString(update,value)
        spUpdate?.apply()
    }
    fun clearStoreSaved(){
        val spEditor = userLocalDataBase?.edit()
        spEditor?.clear()
        spEditor?.apply()
    }

}