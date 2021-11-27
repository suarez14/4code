package com.example.run

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {

        // Splash
        Thread.sleep(1000) //Hack:
        setTheme(R.style.Theme_Design_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        auth = Firebase.auth

        auth = FirebaseAuth.getInstance()

        val buttonLogin = findViewById<Button>(R.id.buttonLoginLogin)
        val loginEmail = findViewById<EditText>(R.id.editTextLoginEmail)
        val loginPassword = findViewById<EditText>(R.id.editTextLoginPassword)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id_auth))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)


        // metodo que lleva a registro con el boton inscribirse
        val loginSignUp = findViewById<TextView>(R.id.textViewLoginSignUp)
        loginSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


        // metodo que lleva a recuperar contraseña  con el boton forgot
        val loginForgot = findViewById<TextView>(R.id.textViewLoginForgot)
        loginForgot.setOnClickListener {
            val intent = Intent(this, ForgotActivity::class.java)
            startActivity(intent)
        }


        // rutina boton google
        buttonLoginGoogle.setOnClickListener {
            signIn()
        }

        // rutina boton login
        buttonLogin.setOnClickListener {

            if (loginEmail.text.isNotEmpty() && loginPassword.text.isNotEmpty()){
                if (isEmail(loginEmail.text.toString()) == true) {
                    if (isPasswoord(loginPassword.text.toString()) == true) {
                        signInuser(loginEmail.text.trim().toString(),loginPassword.text.trim().toString())
                    }else{
                        Toast.makeText(this , getString(R.string.passwordCharacter), Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this , getString(R.string.wrongEmail), Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this , getString(R.string.emptyField), Toast.LENGTH_SHORT).show()
            }
        }
    }

    // funcion login en firebase
    private fun signInuser(email:String, password: String) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){  task ->

            if (task.isSuccessful){
                val user = auth.currentUser        //validar si esta el usario activo
                updateUI(user)
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)

            }else{
                updateUI(null)
                Toast.makeText(this , getString(R.string.wrongEmailPassword), Toast.LENGTH_SHORT).show()
            }

        }
    }
    // funcion que identifica el usuario al inciar la actividad
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    // funcion que si encuentra usuario logueado pasa directo a home
    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser !=null){
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }

    }
    // validar que le imei se escribe correctamente
    private fun isEmail(texto:String):Boolean {
        val emailPattern: Pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        val emailMatcher: Matcher = emailPattern.matcher(texto)
        return emailMatcher.find()

    }
    //

    // validar que el password se escribe con las reglas correctas
    private fun isPasswoord(texto:String):Boolean {
        val passwordPattern: Pattern = Pattern.compile("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}\$")
        val passwordMatcher: Matcher = passwordPattern.matcher(texto)
        return passwordMatcher.find()

    }
    // funcion google login
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }
    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
    // [START onactivityresult]
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user: FirebaseUser = auth.currentUser!!
                    updateUI(user)
                    db.collection("users").document(user.uid).set(
                    hashMapOf("firstName" to user.displayName,
                    "phone" to user.phoneNumber,"email" to user.email))
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }


}