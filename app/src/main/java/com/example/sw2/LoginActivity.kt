package com.example.sw2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var txtUser: EditText
    private lateinit var txtPassword : EditText
    private lateinit var pogressBarLogin: ProgressBar
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        txtUser = findViewById(R.id.editUser)
        txtPassword = findViewById(R.id.editPassword)
        pogressBarLogin = findViewById(R.id.progressBar2)

        auth = FirebaseAuth.getInstance()


    }
    fun forgotPassword(view:View){
        startActivity(Intent(this,ForgotPassowrd::class.java))
    }
    fun register(view:View){
        startActivity(Intent(this,RegistrarActivity::class.java))
    }
    fun login(view:View){
        loginUser()
    }
    private fun loginUser(){

        val user:String = txtUser.text.toString()
        val password:String = txtPassword.text.toString()

        if(TextUtils.isEmpty(user) && TextUtils.isEmpty(password)){
            pogressBarLogin.visibility= View.VISIBLE
            Toast.makeText(this,"entre a loquear",Toast.LENGTH_SHORT).show()
            auth.signInWithEmailAndPassword(user,password).addOnCompleteListener(this){
                task ->
                if(task.isSuccessful){

                    action()
                }else{
                    Toast.makeText(this,"Error en autotentificaio",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun action(){
        startActivity(Intent(this,MainActivity::class.java))

    }
}
