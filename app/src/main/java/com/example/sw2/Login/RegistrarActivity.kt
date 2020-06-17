package com.example.sw2.Login


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter.*
import androidx.appcompat.widget.Toolbar
import com.example.sw2.Clases.ServicioListViewTesteo
import com.example.sw2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_registrar.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class RegistrarActivity : AppCompatActivity() {
    private lateinit var txtName :EditText
    private lateinit var txLasttName :EditText
    private lateinit var txtEmail :EditText
    private lateinit var txtPassword :EditText
    private lateinit var txtphone: EditText
    private lateinit var pogressBar:ProgressBar
    private lateinit var dbReference:DatabaseReference
    private lateinit var database:FirebaseDatabase
    private lateinit var auth:FirebaseAuth
    private lateinit var distrito:String
    private var toolbar:Toolbar? = null
    private var Spinner: Spinner? = null
    //Testeando
    lateinit var Array:ArrayList<ServicioListViewTesteo>
    private lateinit var dbReferenceServicios : DatabaseReference
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)

        txtName = findViewById(R.id.textNombre)
        txLasttName = findViewById(R.id.textApellidos)
        txtEmail = findViewById(R.id.txtCorreo)
        txtPassword = findViewById(R.id.txtPassword)
        txtphone = findViewById(R.id.txtTelefono)
        toolbar = findViewById(R.id.toolbar_register)
        pogressBar = findViewById(R.id.progressBar)
        Spinner = findViewById(R.id.Spinner_distrito_registro)
        var adapter = createFromResource(applicationContext,
            R.array.list_distritos_lima,
            R.layout.spinner_text
        )
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        Spinner?.adapter = adapter
        Spinner?.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                distrito = p0?.getItemAtPosition(p2).toString()
            }
        }

        toolbar?.title = "Creación de usuario"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        dbReference = database.reference.child("User")


        //AgregarFlatantes()
        //IngresandoDatosPrueba()
    }
    fun register(view:View): View {
        CreateNewAccount()
        return view
    }
    private fun IngresandoDatosPrueba(){
        //dbReferenceServicios = database.reference.child("Servicios")
        Array = ArrayList()
        Array.add(ServicioListViewTesteo("","Servicio de gas","Los Olivos","",null,"981077300",5,null))
        Array.add(ServicioListViewTesteo("","Servicio de sanidad","La molina","",null,"981077301",4,null))
        Array.add(ServicioListViewTesteo("","Servicio de transporte público","Comas","",null,"981077302",3,null))
        Array.add(ServicioListViewTesteo("","Servicio de telecomunicaciones","Surco","",null,"981077303",4,null))
        Array.add(ServicioListViewTesteo("","Servicio de transporte público","Surquillo","",null,"981077304",5,null))
        Array.add(ServicioListViewTesteo("","Servicio de vivienda pública","San Juan de Miraflores","",null,"981077305",4,null))
        Array.add(ServicioListViewTesteo("","Servicio de educacion","Barranco","",null,"981077306",3,null))
        Array.add(ServicioListViewTesteo("","Servicio de gas","Pueblo Libre","",null,"981077307",5,null))
        /*
        for(p in Array){
            dbReferenceServicios.push().setValue(p)
        }*/
        for(p in Array){
            var Servicio  = HashMap<String,Any>()
            Servicio["key"] = p.key.toString()
            Servicio["NombreTrabaj"] = p.NombreTrabaj.toString()
            Servicio["Distrito"] =p.Distrito.toString()
            Servicio["Imagen"] = p.Imagen.toString()
            Servicio["ImagenView"] = p.ImagenView.toString()
            Servicio["Telefono"] = p.Telefono.toString()
            Servicio["Calificacion"] =p.Calificacion.toString()
            Servicio["UriImagen"] =p.UriImagen.toString()
            db.collection("Servicios").document().set(Servicio).addOnSuccessListener {
                Toast.makeText(applicationContext,"ga",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun AgregarFlatantes(){
        dbReference.child("jG19WQpsTmSQhsdc4TUfTLHyj4w1").child("Afiliado").setValue("false")
    }
    private fun CreateNewAccount(){
        val name:String = txtName.text.toString()
        val lastname:String = txLasttName.text.toString()
        val email:String = txtEmail.text.toString()
        val password:String = txtPassword.text.toString()
        val phone:String= txtphone.text.toString()
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
                        //String key = mDatabase.child("posts").push().getKey();
                        //Obteniendo llave para tenerla como atributo
                        val userBD = dbReference.child(user?.uid.toString())
                        userBD.child("Nombre").setValue(name)
                        userBD.child("Apellido").setValue(lastname)
                        userBD.child("E-mail").setValue(email)
                        userBD.child("Telefono").setValue(phone)
                        userBD.child("Distrito").setValue(distrito)
                        userBD.child("ID").setValue(user?.uid.toString())
                        userBD.child("Afiliado").setValue("false")
                        action()

                    }else{
                        Toast.makeText(this,"Ha ocurrido un error al en la bae de dato s",Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
    private fun action(){
        startActivity(Intent(this, LoginActivity::class.java))
    }
    private fun VerifyEmail(user:FirebaseUser?){
        Toast.makeText(this,"1",Toast.LENGTH_LONG).show()
        user?.sendEmailVerification()?.addOnCompleteListener(this){
            task ->
            if(task.isComplete){
                Toast.makeText(this,"Correo enviado",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Error al enviar email",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
