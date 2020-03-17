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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener


class LoginActivity : AppCompatActivity() {

    private lateinit var txtUser: EditText
    private lateinit var txtPassword: EditText
    private lateinit var pogressBarLogin: ProgressBar
    private lateinit var mAuthListener : FirebaseAuth
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        txtUser = findViewById(R.id.editUser)
        txtPassword = findViewById(R.id.editPassword)
        pogressBarLogin = findViewById(R.id.progressBar2)



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

        val user: String = txtUser.text.toString()
        val password: String = txtPassword.text.toString()


        auth = FirebaseAuth.getInstance()

        if (!(TextUtils.isEmpty(user) && TextUtils.isEmpty(password))) {
            pogressBarLogin.visibility = View.VISIBLE
            Toast.makeText(this, "entre a loquear", Toast.LENGTH_SHORT).show()
            auth.signInWithEmailAndPassword(user, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                    action()
                } else {
                    Toast.makeText(this, "Error en autotentificaio", Toast.LENGTH_SHORT).show()

                }

            }
        }else{
            Toast.makeText(this, "Si sabes que gobiernas ", Toast.LENGTH_SHORT).show()
        }
    }

    private fun action() {
        startActivity(Intent(this, MainActivity::class.java))

    }
}

