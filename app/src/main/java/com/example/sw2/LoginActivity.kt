package com.example.sw2

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.example.sw2.framents.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener


class LoginActivity : AppCompatActivity() {

    private lateinit var txtUser: EditText
    private lateinit var txtPassword: EditText
    private lateinit var pogressBarLogin: ProgressBar
    private lateinit var mAuthListener : FirebaseAuth
    private lateinit var auth: FirebaseAuth

    private var usuarioProp: String  = "luisbruno777@gmail.com"
    private var contraseñaProp:String = "123456"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        txtUser = findViewById(R.id.editUser)
        txtPassword = findViewById(R.id.editPassword)
        pogressBarLogin = findViewById(R.id.progressBar2)

        window.decorView.apply {
            // Hide both the navigation bar and the status bar.
            // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
            // a general rule, you should design your app to hide the status bar whenever you
            // hide the navigation bar.
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }

    }

    fun forgotPassword(view: View) {
        startActivity(Intent(this, ForgotPassowrd::class.java))
    }

    fun register(view: View) {
        startActivity(Intent(this, RegistrarActivity::class.java))
    }

    fun login(view: View) {
        loginUser()
    }

    private fun loginUser() {
        /*
        val user: String = txtUser.text.toString()
        val password: String = txtPassword.text.toString()*/
        val user:String = usuarioProp
        val password:String = contraseñaProp



        auth = FirebaseAuth.getInstance()

        if (!(TextUtils.isEmpty(user) && TextUtils.isEmpty(password))) {
            pogressBarLogin.visibility = View.VISIBLE
            Toast.makeText(this, "entre a loquear", Toast.LENGTH_SHORT).show()
            auth.signInWithEmailAndPassword(user, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    var intent = Intent(this,MainActivity::class.java)
                    intent.putExtra("NombreUsuario",user)
                    action(intent)
                } else {
                    Toast.makeText(this, "Error en autotentificaio", Toast.LENGTH_SHORT).show()
                }

            }
        }else{
            Toast.makeText(this, "Si sabes que gobiernas ", Toast.LENGTH_SHORT).show()
        }
    }

    private fun action(intent:Intent) {
        startActivity(intent)
        pogressBarLogin.visibility = View.GONE
    }
}

