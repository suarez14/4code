package com.example.run

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    // autenticación con firebase
    private lateinit var auth: FirebaseAuth;



    override fun onCreate(savedInstanceState: Bundle?) {

        // Splash
        Thread.sleep(1000) //Hack:
        setTheme(R.style.Theme_Design_NoActionBar)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance();

        val buttonLogin = findViewById<Button>(R.id.buttonLoginLogin)
        val loginEmail = findViewById<EditText>(R.id.editTextLoginEmail)
        val loginPassword = findViewById<EditText>(R.id.editTextLoginPassword)

        // funcion que lleva a registro con el boton inscribirse
        val loginSignUp = findViewById<TextView>(R.id.textViewLoginSignUp)
        loginSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        buttonLogin.setOnClickListener {
            signInuser(loginEmail.text.trim().toString(),loginPassword.text.trim().toString())

        }


    }

    private fun signInuser(email:String, password: String) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){  task ->

            if (task.isSuccessful){
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)

            }else{
                Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show()
            }

        }
    }

}