package com.example.run

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    //val buttonLogout = findViewById<Button>(R.id.buttonHomeLogout)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)




       // auth = FirebaseAuth.getInstance();

        button.setOnClickListener {
            Firebase.auth.signOut()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
       }


    }
}