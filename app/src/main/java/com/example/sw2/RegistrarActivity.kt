package com.example.sw2


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.sw2.Clases.ServicioListView
import com.example.sw2.Clases.ServicioListViewTesteo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registrar.*

class RegistrarActivity : AppCompatActivity() {


    private lateinit var txtName :EditText
    private lateinit var txLasttName :EditText
    private lateinit var txtEmail :EditText
    private lateinit var txtPassword :EditText
    private lateinit var pogressBar:ProgressBar
    private lateinit var dbReference:DatabaseReference
    private lateinit var database:FirebaseDatabase
    private lateinit var auth:FirebaseAuth

    //Testeando
    public lateinit var Array:ArrayList<ServicioListViewTesteo>
    private lateinit var dbReferenceServicios : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)
        txtName = findViewById(R.id.textNombre)
        txLasttName = findViewById(R.id.textApellidos)
        txtEmail = findViewById(R.id.txtCorreo)
        txtPassword = findViewById(R.id.txtPassword)
        pogressBar = findViewById(R.id.progressBar)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        /*dbReference = database.reference.child("message")
        dbReference.setValue("Hola mundo")
*/
        dbReference = database.reference.child("User")

        //IngresandoDatosPrueba()
    }
    fun register(view:View){
        CreateNewAccount()
    }
    private fun IngresandoDatosPrueba(){
        dbReferenceServicios = database.reference.child("Servicios")
        Array = ArrayList()
        Array.add(ServicioListViewTesteo("","Servicio de gas","Los Olivos","",null,"981077300",5,null))
        Array.add(ServicioListViewTesteo("","Servicio de sanidad","La molina","",null,"981077301",4,null))
        Array.add(ServicioListViewTesteo("","Servicio de transporte público","Comas","",null,"981077302",3,null))
        Array.add(ServicioListViewTesteo("","Servicio de telecomunicaciones","Surco","",null,"981077303",4,null))
        Array.add(ServicioListViewTesteo("","Servicio de transporte público","Surquillo","",null,"981077304",5,null))
        Array.add(ServicioListViewTesteo("","Servicio de vivienda pública","San Juan de Miraflores","",null,"981077305",4,null))
        Array.add(ServicioListViewTesteo("","Servicio de educacion","Barranco","",null,"981077306",3,null))
        Array.add(ServicioListViewTesteo("","Servicio de gas","Pueblo Libre","",null,"981077307",5,null))

        for(p in Array){
            dbReferenceServicios.push().setValue(p)
        }


    }
    private fun CreateNewAccount(){
        val name:String = txtName.text.toString()
        val lastname:String = txLasttName.text.toString()
        val email: String = txtEmail.text.toString()
        val password: String = txtPassword.text.toString()
/*
        var postRef : DatabaseReference = database.reference.child("Servicios")
        var newPost :DatabaseReference = postRef.push()*/
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(lastname) &&
            !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
                progressBar.visibility = View.VISIBLE

            auth.createUserWithEmailAndPassword(email,password).
                addOnCompleteListener(this){
                task ->
                    if(task.isComplete){

                        val user : FirebaseUser?= auth.currentUser
                        VerifyEmail(user)

                        val userBD = dbReference.child(user?.uid.toString())
                        userBD.child("Name").setValue(name)
                        userBD.child("LastName").setValue(lastname)
                        userBD.child("email").setValue(email)
                        action()

                    }else{
                        Toast.makeText(this,"Ha ocurrido un error al en la bae de dato s",Toast.LENGTH_SHORT).show()
                    }
            }
            }
    }
    private fun action(){
        startActivity(Intent(this,LoginActivity::class.java))
    }
    private fun VerifyEmail(user:FirebaseUser?){
        Toast.makeText(this,"1",Toast.LENGTH_LONG).show()
        user?.sendEmailVerification()?.addOnCompleteListener(this){
            task ->
            Toast.makeText(this,"Entre",Toast.LENGTH_LONG).show()
            if(task.isComplete){
                Toast.makeText(this,"Corre enviado",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Error al enviar email",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
