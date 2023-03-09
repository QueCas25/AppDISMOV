package com.example.proyectodismov

import android.content.Intent
import android.os.Bundle
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectodismov.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class SignInActivity : AppCompatActivity()
{

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var cleng:Switch
    private lateinit var correo:TextView
    private lateinit var contra:TextView


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

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


        firebaseAuth = FirebaseAuth.getInstance()
        binding.textView.setOnClickListener {
            val intent = Intent(this, UserSignUpActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            val obtenido=false
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val admin = arrayOf("christopherplumamireles353@gmail.com", "maurv99m69@gmail.com")
            val encontrado = admin.contains(email)
            println(encontrado) // true



            if (email.isNotEmpty() && pass.isNotEmpty())
            {
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful)
                    {
                        val intent = Intent(this, MainActivity::class.java)
                        val intent2 = Intent(this, MainActivity2::class.java)
                        val user = firebaseAuth.currentUser
                        val verifica = user?.isEmailVerified()
                        obtenido

                        if(verifica==true && encontrado==true )
                        {
                            startActivity(intent2)
                        }
                        if(verifica==true && encontrado==false) {
                            startActivity(intent)
                        }
                        else
                        {
                            Toast.makeText(this, "¡Verifique su correo electronico!", Toast.LENGTH_SHORT).show()
                        }

                    } else
                    {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT)
                        Toast.makeText(this,"Su correo o contraseña son incorrectos", Toast.LENGTH_SHORT).show()
                    }
                }

            }
            else
            {
                Toast.makeText(this, "¡Tiene que llenar todos los campos!", Toast.LENGTH_SHORT).show()

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
