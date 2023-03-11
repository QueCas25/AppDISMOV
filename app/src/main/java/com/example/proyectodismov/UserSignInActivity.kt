package com.example.proyectodismov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyectodismov.databinding.ActivityUserSignInBinding
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import database.usuario
import java.util.*


class UserSignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserSignInBinding
    lateinit var correoe:String
    lateinit var contrasena:String
    lateinit var auth:FirebaseAuth
    private lateinit var cleng: Switch
    private lateinit var correo: TextView
    private lateinit var contra: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserSignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        auth = Firebase.auth

        binding.button.setOnClickListener{
        correoe = binding.emailEt.text.toString()
        contrasena = binding.passET.text.toString()

            if(correoe.isBlank() || contrasena.isBlank())
            {
                binding.emailEt.setError("En blanco")
                binding.passET.setError("En blanco")
                Toast.makeText(applicationContext,"Por favor ingresa tus datos", Toast.LENGTH_SHORT).show()
            }
            else
            {
                autenticariniciodeSesion(correoe, contrasena)
            }

            }

        binding.textView.setOnClickListener {
            val intent = Intent(this, UserSignUpActivity::class.java)
            startActivity(intent)
        }


        cleng = findViewById(R.id.cleng)
        correo= findViewById(R.id.EmailLogin)
        contra= findViewById(R.id.ContraLogin)

        cleng.setOnCheckedChangeListener{ _, isChecked ->
            if(isChecked)
            {
                actualizarResource("en")
            }
            else
            { actualizarResource("es")
            }
        }
        }


    fun autenticariniciodeSesion(correoe:String, contrasena:String )
    {
        auth.signInWithEmailAndPassword(correoe, contrasena).addOnCompleteListener(this){ task ->
            if (task.isSuccessful) {
                startActivity(Intent(this, MainActivity::class.java))
            }
            else
            {
                Toast.makeText(this, "El usuario no esta registrado", Toast.LENGTH_SHORT).show()
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

        cleng.text = recursos.getString(R.string.cleng)
        binding.button.text= recursos.getString(R.string.InicioSesion)
        binding.textView.text = recursos.getString(R.string.NoRegistrado)
        correo.text = recursos.getString(R.string.EmailLogin)
        contra.text = recursos.getString(R.string.ContraLogin)
    }

    }