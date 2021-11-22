package com.example.run


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference.child("Users")


        // funcion que lleva a login  con el boton login
        textViewRegisterLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


        buttonRegisterRegister.setOnClickListener {
            if (editTextRegisterName.text.isNotEmpty() && editTextRegisterLastName.text.isNotEmpty() && editTextRegisterPhone.text.isNotEmpty() && editTextRegisterEmail.text.isNotEmpty()
                && editTextRegisterPassword.text.isNotEmpty() && editTextRegisterConfirmPassword.text.isNotEmpty()){

                if (isEmail(editTextRegisterEmail.text.toString()) == true) {

                    if (isPasswoord(editTextRegisterPassword.text.toString()) == true) {

                        if (editTextRegisterPassword.text.toString() == editTextRegisterConfirmPassword.text.toString()) {

                            if (isWord(editTextRegisterName.text.toString()) == true) {

                                if(isWord(editTextRegisterLastName.text.toString()) == true) {

                                    if(isNumber(editTextRegisterPhone.text.toString()) == true) {

                                        if (checkBoxRegisterPolitics.isChecked()) {

                                            auth.createUserWithEmailAndPassword(editTextRegisterEmail.text.toString(), editTextRegisterPassword.text.toString()).addOnCompleteListener(this) { task ->
                                                if (task.isSuccessful) {

                                                    val user: FirebaseUser = auth.currentUser!!
                                                    verifyEmail(user)
                                                    val currentUserDb = databaseReference.child(user.uid)
                                                    currentUserDb.child("firstName").setValue(editTextRegisterName.text.toString())
                                                    currentUserDb.child("lastName").setValue(editTextRegisterLastName.text.toString())
                                                    currentUserDb.child("phone").setValue(editTextRegisterPhone.text.toString())
                                                    currentUserDb.child("email").setValue(editTextRegisterEmail.text.toString())
                                                    Toast.makeText(this , getString(R.string.successfulRegistration), Toast.LENGTH_SHORT).show()
                                                    val intent = Intent(this, HomeActivity::class.java)
                                                    startActivity(intent)
                                                }else {
                                                    Toast.makeText(this , getString(R.string.emailRegistered), Toast.LENGTH_SHORT).show()
                                                }
                                            }

                                        }else{
                                            Toast.makeText(this , getString(R.string.acceptPolicies1), Toast.LENGTH_SHORT).show()
                                        }

                                    }else {
                                        Toast.makeText(this , getString(R.string.writePhone), Toast.LENGTH_SHORT).show()
                                    }
                                }else {
                                    Toast.makeText(this , getString(R.string.writeLastName), Toast.LENGTH_SHORT).show()
                                }
                            }else{
                                Toast.makeText(this , getString(R.string.writeName), Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            Toast.makeText(this , getString(R.string.passwordSame), Toast.LENGTH_SHORT).show()
                        }
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

    private fun verifyEmail(user: FirebaseUser) {
        user.sendEmailVerification()
            .addOnCompleteListener(this) {
                    task ->
                if (task.isSuccessful) {
                    Toast.makeText(this , getString(R.string.email) + user.getEmail(), Toast.LENGTH_SHORT).show()
                   // Toast.makeText(this, "Email " + user.getEmail(), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this , getString(R.string.verifymail), Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun isEmail(texto:String):Boolean {
        val emailPattern: Pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        val emailMatcher: Matcher = emailPattern.matcher(texto)
        return emailMatcher.find()

    }
    private fun isPasswoord(texto:String):Boolean {
        val passwordPattern: Pattern = Pattern.compile("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}\$")
        val passwordMatcher: Matcher = passwordPattern.matcher(texto)
        return passwordMatcher.find()

    }
    private fun isWord(texto:String):Boolean {
        val wordPattern: Pattern = Pattern.compile("^[a-zA-Z]{4,}(?: [a-zA-Z]+){0,2}\$")
        val wordMatcher: Matcher = wordPattern.matcher(texto)
        return wordMatcher.find()

    }
    private fun isNumber(texto:String):Boolean{
        try {
            texto.toInt()
            return true
        }catch (e:Exception){
            return false
        }

    }

}