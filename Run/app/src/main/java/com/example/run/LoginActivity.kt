package com.example.run

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {



        // Splash
        Thread.sleep(2000) //Hack:
        setTheme(R.style.Theme_Design_NoActionBar)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // funcion que lleva a registro con el boton inscribirse
        val loginSignUp = findViewById<TextView>(R.id.textViewLoginSignUp)
        loginSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

}