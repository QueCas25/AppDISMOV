package com.example.proyectodismov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyectodismov.databinding.ActivityVideoLlamadaBinding
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import com.example.proyectodismov.databinding.ActivityUserSignUpBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import database.usuario
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import org.jitsi.meet.sdk.JitsiMeet
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiInitializer
import org.jitsi.meet.sdk.JitsiMeetView
import org.jitsi.meet.sdk.JitsiMeetActivityInterface
import org.jitsi.meet.sdk.JitsiMeetActivityDelegate
import org.jitsi.meet.sdk.JitsiMeetOngoingConferenceService
import org.jitsi.meet.sdk.JitsiMeetUserInfo

class VideoLlamada : AppCompatActivity() {

    private lateinit var binding: ActivityVideoLlamadaBinding
    lateinit var auth:FirebaseAuth

    private lateinit var cleng6: Switch
    private lateinit var porfavorma: TextView
    lateinit var database: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoLlamadaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        auth = Firebase.auth

        if(auth.currentUser == null) {
            Toast.makeText(applicationContext, "Por favor logeame", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, UserSignInActivity::class.java))
            finish()
        }

            val serverUrl:URL
            try {
                serverUrl = URL("https://meet.jit.si")
                val options = JitsiMeetConferenceOptions.Builder()
                    .setServerURL(serverUrl)
                    .setFeatureFlag("welcomepage.enabled",false)
                    .build();
                JitsiMeet.setDefaultConferenceOptions(options)
            }
            catch (e:MalformedURLException)
            {
                Toast.makeText(applicationContext, "Ocurrio un error", Toast.LENGTH_SHORT).show()
            }

            binding.joinBtn.setOnClickListener{
                val options = JitsiMeetConferenceOptions.Builder()
                    .setRoom(binding.codeBox.text.toString())
                    .setFeatureFlag("welcomepage.enabled",false)
                    .build();
                JitsiMeetActivity.launch(this, options)
            }

            binding.logOut.setOnClickListener{
                auth.signOut()
                startActivity(Intent(this, UserSignInActivity::class.java))
                finish()
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

        cleng6.text = recursos.getString(R.string.cleng6)
        porfavorma.text = recursos.getString(R.string.porfavor)
        binding.logOut.text = recursos.getString(R.string.logoutbutton)
        binding.joinBtn.text = recursos.getString(R.string.joinbutton)

    }

    }
