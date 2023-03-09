package com.example.proyectodismov

import android.content.Intent
import android.os.Bundle
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.example.proyectodismov.databinding.ActivitySignUpBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var cleng3: Switch
    private lateinit var userregis: TextView
    private lateinit var emailregis: TextView
    private lateinit var contraregis: TextView
    private lateinit var contrarepetida: TextView

    lateinit var auth:FirebaseAuth
    lateinit var database: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        cleng3 = findViewById(R.id.cleng5)
        emailregis= findViewById(R.id.emailregis)
        contraregis= findViewById(R.id.contraregis)
        contrarepetida= findViewById(R.id.contrarepetida)

        cleng3.setOnCheckedChangeListener{ _, isChecked ->
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

            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()



            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {

                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, SignInActivity::class.java)
                            sendEmailVerification()
                            startActivity(intent)
                        }
                        else
                        {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT)
                            Toast.makeText(this,"Su correo o contraseña son incorrectos", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else
                {
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                }
            } else
                 {
                Toast.makeText(this, "Los campos vacios no estan permitidos", Toast.LENGTH_SHORT).show()

                }
        }
    }

        private fun sendEmailVerification()
        {
            val user = firebaseAuth.currentUser!!
            user.sendEmailVerification().addOnCompleteListener(this){
                if(it.isSuccessful)
                {
                    Toast.makeText(this, "Verifique su cuenta a traves del correo de autenticacion", Toast.LENGTH_SHORT).show()
                }
                else
                {

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

        cleng3.text = recursos.getString(R.string.cleng3)
        userregis.text = recursos.getString(R.string.userregis)
        emailregis.text = recursos.getString(R.string.emailregis)
        contraregis.text = recursos.getString(R.string.contraregis)
        contrarepetida.text = recursos.getString(R.string.contrarepetida)
        contrarepetida.text = recursos.getString(R.string.contrarepetida)
        binding.textView.text = recursos.getString(R.string.yatienecuenta)
        binding.button.text= recursos.getString(R.string.registrarse)

    }


}