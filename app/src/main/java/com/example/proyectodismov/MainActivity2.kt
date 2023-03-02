package com.example.proyectodismov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import java.util.*

private lateinit var cerrarsesion2: Button
private lateinit var cleng4: Switch
private lateinit var esadmin: TextView

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        cleng4 = findViewById(R.id.cleng4)
        esadmin = findViewById(R.id.hadmin)
        cleng4.setOnCheckedChangeListener{ _, isChecked ->
            if(isChecked)
            {
                actualizarResource("en")
            }
            else
            { actualizarResource("es")
            }
        }


            cerrarsesion2 = findViewById(R.id.closesesion2)
            cerrarsesion2.setOnClickListener{
                val intent: Intent = Intent(this, SignInActivity:: class.java)
                startActivity(intent)
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

        cleng4.text = recursos.getString(R.string.cleng2)
        esadmin.text = recursos.getString(R.string.esadmin)
        cerrarsesion2.text = recursos.getString(R.string.closesesion)
    }



    }