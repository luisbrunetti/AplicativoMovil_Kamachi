package com.example.sw2.Clases

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

class FirebaseConexion{
    var userLocalDataBase : SharedPreferences? = null
    @Volatile private var context:Context? =null
    private val SP_NAME = "userdetails"
    /*
    private object Holder{
        val instance = FirebaseConexion()
    }
    companion object fun Inicia(context: Context) : FirebaseConexion{
        val instance: FirebaseConexion by lazy {  Holder.instance }
        IniciaruserLocalDataBase(context)
        return instance!!
    }*/
    /*
    private fun IniciaruserLocalDataBase(context:Context){
        userLocalDataBase = context.getSharedPreferences(SP_NAME,0)
    }*/
    constructor(context:Context){
        this.context = context
        userLocalDataBase = context.getSharedPreferences(SP_NAME,0)
    }
    // Creando el patron de diseño Singleton con Kotlin
    companion object{
        @Volatile private var INSTANCE : FirebaseConexion? = null
        @InternalCoroutinesApi
        fun getinstance(Contexto_intanciar:Context) : FirebaseConexion {
            if(INSTANCE == null){
                synchronized(this){
                    INSTANCE = FirebaseConexion(Contexto_intanciar)
                    Log.d("InstanciaFire","Se ha creado la primer instancia de Conexión A Firebase")
                }
            }else{
                Log.d("InstanciaFire","Ya se ha creado la sintacia de Conexión firebase")
            }
            return INSTANCE!!
        }
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