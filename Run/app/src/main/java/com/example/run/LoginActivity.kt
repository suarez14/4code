package com.example.run

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Matcher
import java.util.regex.Pattern

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
        // funcion que lleva a recuperar contraseña  con el boton forgot
        val loginForgot = findViewById<TextView>(R.id.textViewLoginForgot)
        loginForgot.setOnClickListener {
            val intent = Intent(this, ForgotActivity::class.java)
            startActivity(intent)
        }

        buttonLogin.setOnClickListener {

            if (loginEmail.text.isNotEmpty() && loginPassword.text.isNotEmpty()){

                if (isEmail(loginEmail.text.toString()) == true) {

                    if (isPasswoord(loginPassword.text.toString()) == true) {

                        signInuser(loginEmail.text.trim().toString(),loginPassword.text.trim().toString())

                    }else{
                        Toast.makeText(this, "La contraseña debe tener mas de 8 caracteres , al menos un dígito, al menos una minúscula y al menos una mayúscula.\n" +
                                "NO puede tener otros símbolos.", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, "email incorrecto", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "ingresa informacion en todos los campos", Toast.LENGTH_SHORT).show()
            }

        }


    }


    private fun signInuser(email:String, password: String) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){  task ->

            if (task.isSuccessful){
                //validar si est ael suario activo
                val user = auth.currentUser
                updateUI(user)
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)

            }else{
                updateUI(null)
                Toast.makeText(this, "email o password incorrecto", Toast.LENGTH_SHORT).show()
            }

        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser !=null){
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }else{
        }

    }

    private fun isEmail(texto:String):Boolean {
        val emailPattern: Pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        val emailMatcher: Matcher = emailPattern.matcher(texto)
        return emailMatcher.find()

    }
    private fun isPasswoord(texto:String):Boolean {
        val passwordPattern: Pattern =
            Pattern.compile("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}\$")
        val passwordMatcher: Matcher = passwordPattern.matcher(texto)
        return passwordMatcher.find()

    }
}