package com.example.proyectodismov

import android.content.Intent
import android.os.Bundle
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

import com.example.proyectodismov.databinding.ActivityUserSignUpBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
import database.usuario
import org.w3c.dom.Text
import java.util.*

class UserSignUpActivity: AppCompatActivity() {

    private lateinit var binding: ActivityUserSignUpBinding
    private lateinit var cleng5: Switch
    private lateinit var userregis: TextView
    private lateinit var emailregis: TextView
    private lateinit var contraregis: TextView
    private lateinit var contrarepetida: TextView
    lateinit var nombreusuario:String
    lateinit var correoe:String
    lateinit var contrasena:String
    lateinit var confirmPas:String
    private lateinit var auth:FirebaseAuth
    lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        database = FirebaseFirestore.getInstance()

        cleng5 = findViewById(R.id.cleng5)
        userregis= findViewById(R.id.usuarioregistrado)
        emailregis= findViewById(R.id.emailregis)
        contraregis= findViewById(R.id.contraregis)
        contrarepetida= findViewById(R.id.contrarepetida)

        cleng5.setOnCheckedChangeListener{ _, isChecked ->
            if(isChecked)
            {
                actualizarResource("en")
            }
            else
            { actualizarResource("es")
            }
        }

        binding.textView.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {

            correoe = binding.emailEt.text.toString()
            contrasena = binding.passET.text.toString()
            confirmPas = binding.confirmPassEt.text.toString()
            nombreusuario = binding.usuarioEt.text.toString()
            if (nombreusuario.isBlank() || correoe.isBlank() || contrasena.isBlank() || confirmPas.isBlank())
            {
                Toast.makeText (applicationContext, "Please fill up the credential", Toast.LENGTH_SHORT).show()
            }
            else if(contrasena.length< 6 ||  confirmPas.length< 6)
            {
                binding.passET.setError("La contraseña debe tener almenos 6 caracteres")
            }
            else
            {
                if (contrasena==confirmPas)
                {
                    autenticarRegistro(correoe, contrasena, nombreusuario)
                }
                else
                {
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                }
            }
        }

        }


    private fun sendEmailVerification()
    {
        val user = auth.currentUser!!
        user.sendEmailVerification().addOnCompleteListener(this){ task ->
            if(task.isSuccessful)
            {
                Toast.makeText(this, "Verifique su cuenta a traves del correo de autenticacion", Toast.LENGTH_SHORT).show()
            }
            else
            {

            }
        }
    }

    fun autenticarRegistro(correoe:String, contrasena:String , nombreusuario:String) {
        val superusuario = usuario(correoe, contrasena,nombreusuario)
            auth.createUserWithEmailAndPassword(correoe, contrasena).addOnCompleteListener{task ->
                Toast.makeText(this, "Estoy ejecutandome Javi la funcion 22", Toast.LENGTH_SHORT).show()
                if (task.isSuccessful) {
                    database.collection("Usuarios")
                        .document()
                        .set(superusuario)
                        .addOnSuccessListener {
                            val intent = Intent(this, SignInActivity::class.java)
                            sendEmailVerification()
                            startActivity(intent)
                        }
                }
                else
                {
                    Toast.makeText(this, "No hemos podido autenticar", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun actualizarResource(cambio:String){
        val recursos = resources
        val displayMetrics = recursos.displayMetrics
        val configuracion  = resources.configuration
        configuracion.setLocale(Locale(cambio))
        recursos.updateConfiguration(configuracion, displayMetrics)
        configuracion.locale = Locale(cambio)
        resources.updateConfiguration(configuracion, displayMetrics)

        cleng5.text = recursos.getString(R.string.cleng5)
        userregis.text = recursos.getString(R.string.userregis1)
        emailregis.text = recursos.getString(R.string.emailregis)
        contraregis.text = recursos.getString(R.string.contraregis)
        contrarepetida.text = recursos.getString(R.string.contrarepetida)
        contrarepetida.text = recursos.getString(R.string.contrarepetida)
        binding.textView.text = recursos.getString(R.string.yatienecuenta)
        binding.button.text= recursos.getString(R.string.registrarse)

    }


}
