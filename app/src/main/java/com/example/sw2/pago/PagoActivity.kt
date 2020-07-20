package com.example.sw2.pago

import android.app.Service
import android.content.DialogInterface
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.example.sw2.Clases.ServicioListView
import com.example.sw2.Clases.Usuario
import com.example.sw2.R
import com.example.sw2.patrones_diseño.singleton.FirebaseConexion
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_pago.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class PagoActivity : AppCompatActivity() {
    private var toolbar_pago: Toolbar? = null
    private var pago: String? = null
    var key : String? = null
    private var user_info : Usuario?  = null
    private var service_pay: ServicioListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pago)
        ////////////////////////////////////////////////////
        toolbar_pago = findViewById(R.id.toolbar_Pago)
        toolbar_pago?.title = "Pago del servicio"
        setSupportActionBar(toolbar_pago)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        ////////////////////////////////////////////////////
        pago = intent.getStringExtra("cost")
        key = intent.getStringExtra("key")
        service_pay = intent.extras?.getSerializable("service") as ServicioListView
        Log.d("a",service_pay!!.empresa)
        tvi_pago_cost.text = intent.getStringExtra("cost")
        /////////////////////////////////////////////////////////
        user_info = FirebaseConexion(applicationContext).getStoreSaved()
        //////////////////////////////////////////////////////

        but_pay.setOnClickListener {
            //pay()
            alertPago()
        }
    }

    private fun getback(){
        AlertDialog.Builder(this).setTitle("¿Desea salir de la pasarela de pago?").setMessage("Si sale no se guardaran los cambios")
            .setPositiveButton(android.R.string.yes
            ) { p0, p1 ->
                finish()
            }
            .setNegativeButton(android.R.string.no
            ) { p0, p1 ->
            }
            .show()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> getback()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun verificarExpiryDate(expprydate:String):Boolean{
        //val reg = "/^(0[1-9]|1[0-2])\\/?([0-9]{4}|[0-9]{2})\$/"
        val reg ="(0[1-9]|10|11|12)/20[0-9]{2}\$"
        val patern = Pattern.compile(reg)
        val match = patern.matcher(expprydate)
        Log.d("match",match.matches().toString())
        return match.matches()
    }
    private fun verificarCVV(cvv:String):Boolean{
        return Pattern.compile("[0-9]{3}").matcher(cvv).matches()
    }
    private fun VerificarNombrePago(name :String): Boolean {
        val reg = "(^[a-zA-Z]{3,}(?: [a-zA-Z]+){0,2}\$?)"
        val ptern = Pattern.compile(reg)
        val matcher = ptern.matcher(name)
        Log.d("regrex",matcher.matches().toString())
        return matcher.matches()
    }
    private fun VerificarApellidoPago(lastname:String):Boolean{
        val reg = "[a-zA-Z]*[\\s]{1}[a-zA-Z].*"
        val ptern = Pattern.compile(reg)
        val matcher = ptern.matcher(lastname)
        Log.d("regrex2",matcher.matches().toString())
        return matcher.matches()
    }
    private fun verifyEmailPago(email:String) : Boolean{

        return if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(applicationContext,"Ingrese un correo electronico valido",Toast.LENGTH_SHORT).show()
            false
        }else{
            true
        }

    }
    private fun savePayFirebase(){
        val instance = FirebaseDatabase.getInstance().reference
        val keyPush = instance.push().key
        val refinstance = instance.child("pagos").child(keyPush!!)
        refinstance.child("ID_usuario").setValue(user_info!!.ID!!)
        refinstance.child("ID_Pago").setValue(keyPush)
        refinstance.child("ID_Servicio").setValue(service_pay!!.key)
        refinstance.child("ID_Afiliado").setValue(service_pay!!.ID_Ailiado)
        refinstance.child("Mount").setValue(pago.toString())
        refinstance.child("Nombre_servicio_contratado").setValue(service_pay!!.nombreTrabajo)
        refinstance.child("Uri_Servicio_Contratado").setValue(service_pay!!.Uri)
        refinstance.child("Estado").setValue("progreso")
        refinstance.child("Date").setValue(SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date()))
        refinstance.child("Duración").setValue(service_pay!!.duracion)
        refinstance.child("Empresa").setValue(service_pay!!.empresa)
    }
    private fun alertPago(){
        AlertDialog.Builder(this).setTitle("¿Desea cancelar la suma de S/.$pago ?").setMessage("Confirme la transsación porfavor")
            .setPositiveButton(android.R.string.yes
            ) { p0, p1 ->
                progress_pago.visibility = View.GONE
                savePayFirebase()
                Toast.makeText(applicationContext,"Pago registrado con exito",Toast.LENGTH_SHORT).show()
                finish()
            }
            .setNegativeButton(android.R.string.no
            ) { p0, p1 ->
                progress_pago.visibility = View.GONE
            }
            .show()
    }
    private fun pay(){
        progress_pago.visibility = View.VISIBLE
        if(et_cardnumber_pago.text.toString().isNotEmpty() &&
            et_cvv_pago.text.toString().isNotEmpty()&&
            et_venccimiento_pago.text.toString().isNotEmpty()&&
            et_name_pago.text.toString().isNotEmpty() &&
            et_lastname_pago.text.toString().isNotEmpty() &&
            et_email_pago.text.toString().isNotEmpty()){

            if(et_cardnumber_pago.text.toString().length < 16){
                progress_pago.visibility = View.GONE
                Toast.makeText(applicationContext,"Ingrese bien el numero de su tarjeta (16)",Toast.LENGTH_SHORT).show()
            }else{
                if (verificarExpiryDate(et_venccimiento_pago.text.toString())){
                    if(verificarCVV(et_cvv_pago.text.toString())){
                        if (VerificarNombrePago(et_name_pago.text.toString())){
                            if(VerificarApellidoPago(et_lastname_pago.text.toString())){
                                if(verifyEmailPago(et_email_pago.text.toString())){
                                    alertPago()
                                }else{
                                    progress_pago.visibility = View.GONE
                                    Toast.makeText(applicationContext,"Escriba un email de valido",Toast.LENGTH_SHORT).show()
                                }
                            }else{
                                progress_pago.visibility = View.GONE
                                Toast.makeText(applicationContext,"Escriba un apellido de valido",Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            progress_pago.visibility = View.GONE
                            Toast.makeText(applicationContext,"Escriba un noombre de valido",Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        progress_pago.visibility = View.GONE
                        Toast.makeText(applicationContext,"Escriba bien el CVV",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    progress_pago.visibility = View.GONE
                    Toast.makeText(applicationContext,"Escriba un dia de experiación valido",Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            progress_pago.visibility = View.GONE
            Toast.makeText(applicationContext,"Complete todo los campos",Toast.LENGTH_SHORT).show()
        }
    }
}