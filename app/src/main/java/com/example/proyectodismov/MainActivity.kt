package com.example.proyectodismov


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    val btn:Button = findViewById(R.id.closesesion)
        btn.setOnClickListener{
            val intent: Intent = Intent(this, SignInActivity:: class.java)
            startActivity(intent)
                }
             }

            }
