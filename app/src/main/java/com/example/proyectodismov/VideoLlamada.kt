package com.example.proyectodismov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyectodismov.databinding.ActivityVideoLlamadaBinding
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
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
                    .setAudioMuted(true)
                    .setVideoMuted(true)
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
                    .setAudioMuted(true)
                    .setVideoMuted(true)
                    .build()
                JitsiMeetActivity.launch(this, options)
            }

            binding.logOut.setOnClickListener{
                auth.signOut()
                startActivity(Intent(this, UserSignInActivity::class.java))
                finish()
            }


        }




    }
