package com.example.run

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance();

        val buttonRegisterRegister = findViewById<Button>(R.id.buttonRegisterRegister)
        val editTextRegisterEmail = findViewById<EditText>(R.id.editTextRegisterEmail)
        val editTextRegisterPassword = findViewById<EditText>(R.id.editTextRegisterPassword)


        buttonRegisterRegister.setOnClickListener {
            auth.createUserWithEmailAndPassword(editTextRegisterEmail.text.toString(), editTextRegisterPassword.text.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                val intent = Intent(this, HomeActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show()
                        }

            }

        }

    }


}