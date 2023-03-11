package com.example.proyectodismov


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Switch
import android.widget.TextView
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import java.util.*



class MainActivity : AppCompatActivity() {
    private lateinit var cleng2: Switch
    private lateinit var bienve: TextView
    private lateinit var cerrarsesion: Button
    private lateinit var videollamada: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cleng2 = findViewById(R.id.cleng2)
        bienve = findViewById(R.id.bienve)
        cleng2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                actualizarResource("en")
            } else {
                actualizarResource("es")
            }
        }

        cerrarsesion = findViewById(R.id.closesesion)
        cerrarsesion.setOnClickListener {
            val intent: Intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)

        }
        videollamada = findViewById(R.id.videocall)
        videollamada.setOnClickListener {
            val intent: Intent = Intent(this, VideoLlamada::class.java)
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

        cleng2.text = recursos.getString(R.string.cleng2)
        bienve.text = recursos.getString(R.string.bienve)
        cerrarsesion.text = recursos.getString(R.string.closesesion)
        videollamada.text = recursos.getString(R.string.videoboton)
    }
}