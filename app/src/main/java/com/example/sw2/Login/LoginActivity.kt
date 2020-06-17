package com.example.sw2.Login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sw2.patrones_diseño.singleton.FirebaseConexion
import com.example.sw2.Clases.Usuario
import com.example.sw2.MainActivity
import com.example.sw2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.InternalCoroutinesApi


class LoginActivity : AppCompatActivity() {

    private lateinit var txtUser: EditText
    private lateinit var txtPassword: EditText
    private lateinit var pogressBarLogin: ProgressBar
    private lateinit var mAuthListener : FirebaseAuth
    private lateinit var auth: FirebaseAuth
    private lateinit var botonIniciarSesion:Button

    private var usuarioProp: String  = "test@gmail.com"
    private var contraseñaProp:String = "123456"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        txtUser = findViewById(R.id.editUser)
        botonIniciarSesion = findViewById(R.id.Login)
        txtPassword = findViewById(R.id.editPassword)
        pogressBarLogin = findViewById(R.id.progressBar2)

    }
    fun forgotPassword(view: View) {
        startActivity(Intent(this, ForgotPassowrd::class.java))
    }
    fun register(view: View) {
        startActivity(Intent(this, RegistrarActivity::class.java))
    }
    @InternalCoroutinesApi
    fun login(view: View) {
        loginUser()
    }

    override fun onResume() {
        super.onResume()
        botonIniciarSesion.isEnabled = true
    }
    @InternalCoroutinesApi
    private fun ObtenerDatosUsuario(user:String){
        val intentTest = FirebaseConexion.getinstance(applicationContext)
        var query: Query =
            FirebaseDatabase.getInstance().reference.child("User").orderByChild("E-mail")
                .equalTo(user)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(p0: DataSnapshot) {
                var instancia:Usuario? = null
                Toast.makeText(applicationContext,p0.child("ID").value.toString(),Toast.LENGTH_SHORT).show()
                for (p0 in p0.children) {
                    instancia= Usuario(p0.child("Nombre").value.toString(),p0.child("Apellido").value.toString()
                    ,p0.child("Distrito").value.toString(),p0.child("E-mail").value.toString(),
                        p0.child("Telefono").value.toString(),p0.child("UrlImagen").value.toString()
                        ,p0.child("ID").value.toString(),p0.child("ID_Afiliado").value.toString()
                        ,p0.child("Afiliado").value.toString().toBoolean()
                    )
                }
                if (instancia != null) {
                    Toast.makeText(applicationContext,"ENTRE",Toast.LENGTH_SHORT).show()
                    intentTest.clearStoreSaved()
                    intentTest.StoreUserDate(instancia)
                }
            }
        })
    }
    @InternalCoroutinesApi
    private fun loginUser() {
        /*val user: String = txtUser.text.toString()
        val password: String = txtPassword.text.toString()*/
        botonIniciarSesion.isEnabled = false
        var user:String = usuarioProp
        var password:String = contraseñaProp
        auth = FirebaseAuth.getInstance()
        if (!(TextUtils.isEmpty(user) && TextUtils.isEmpty(password))) {
            pogressBarLogin.visibility = View.VISIBLE
            auth.signInWithEmailAndPassword(user, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    ObtenerDatosUsuario(user)
                    var intent = Intent(this, MainActivity::class.java)
                    action(intent)
                } else {
                    this.pogressBarLogin.visibility = View.GONE
                    botonIniciarSesion.isEnabled = true
                    Toast.makeText(this, "Error en autotentificación", Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            this.pogressBarLogin.visibility = View.GONE
            botonIniciarSesion.isEnabled = true
            Toast.makeText(this, "Ingrese conrectamente su email y contraseña", Toast.LENGTH_SHORT).show()
        }
    }

    private fun action(intent:Intent) {
        startActivity(intent)
        pogressBarLogin.visibility = View.GONE
    }
}

