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
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    // autenticación con firebase
    private lateinit var auth: FirebaseAuth;
    var cbxAcceptPolicy:Boolean = false
    var btnOkPolicy:Boolean = false
    var policyFrag : Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)



        auth = FirebaseAuth.getInstance();
        linlay_policy_container.isVisible = false
        linlay_policy_container.isEnabled = false


        // funcion que lleva a login  con el boton login
        textViewRegisterLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        buttonRegisterRegister.setOnClickListener {
            if (!cbxAcceptPolicy){
                buttonRegisterRegister.isEnabled = false
                linlay_policy_container.isVisible = true
                linlay_policy_container.isEnabled = true
                //policyFrag = FragmentPolicy.newInstance("","")
                //loadPolicyFragment(policyFrag as FragmentPolicy)
            }else{
                if (editTextRegisterName.text.isNotEmpty() && editTextRegisterLastName.text.isNotEmpty() && editTextRegisterPhone.text.isNotEmpty() && editTextRegisterEmail.text.isNotEmpty()
                    && editTextRegisterPassword.text.isNotEmpty() && editTextRegisterConfirmPassword.text.isNotEmpty()){

                    if (isEmail(editTextRegisterEmail.text.toString()) == true) {

                        if (isPasswoord(editTextRegisterPassword.text.toString()) == true) {

                            if (editTextRegisterPassword.text.toString() == editTextRegisterConfirmPassword.text.toString()) {

                                if (isWord(editTextRegisterName.text.toString()) == true) {

                                    if(isWord(editTextRegisterLastName.text.toString()) == true) {

                                        if(isNumber(editTextRegisterPhone.text.toString()) == true) {

                                            if (cbxAcceptPolicy) {
                                            //if (checkBoxRegisterPolitics.isChecked()) {

                                                auth.createUserWithEmailAndPassword(editTextRegisterEmail.text.toString(), editTextRegisterPassword.text.toString()).addOnCompleteListener(this) { task ->
                                                    if (task.isSuccessful) {
                                                        Toast.makeText(this, "registro exitoso", Toast.LENGTH_SHORT).show()
                                                        val intent = Intent(this, HomeActivity::class.java)
                                                        startActivity(intent)
                                                    }else {
                                                        Toast.makeText(this, "El correo ya se encuentra registrado", Toast.LENGTH_SHORT).show()
                                                    }
                                                }

                                            }else{
                                                Toast.makeText(this, "Debe Aceptar las politcas", Toast.LENGTH_SHORT).show()
                                            }

                                        }else {
                                            Toast.makeText(this, "Escriba su telefono", Toast.LENGTH_SHORT).show()
                                        }
                                    }else {
                                        Toast.makeText(this, "Escriba sus Apellidos", Toast.LENGTH_SHORT).show()
                                    }
                                }else{
                                    Toast.makeText(this, "Escriba sus Nombres", Toast.LENGTH_SHORT).show()
                                }
                            }else{
                                Toast.makeText(this, "las contraseñas debe ser iguales", Toast.LENGTH_SHORT).show()
                            }
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

        /*
        buttonRegisterRegister.setOnClickListener {
            if (!cbxAcceptPolicy){
                buttonRegisterRegister.isEnabled = false
                linlay_policy_container.isVisible = true
                linlay_policy_container.isEnabled = true
            }
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
                                                    Toast.makeText(this, "registro exitoso", Toast.LENGTH_SHORT).show()
                                                    val intent = Intent(this, HomeActivity::class.java)
                                                    startActivity(intent)
                                                }else {
                                                    Toast.makeText(this, "El correo ya se encuentra registrado", Toast.LENGTH_SHORT).show()
                                                }
                                            }

                                        }else{
                                            Toast.makeText(this, "Debe Aceptar las politcas", Toast.LENGTH_SHORT).show()
                                        }

                                    }else {
                                        Toast.makeText(this, "Escriba su telefono", Toast.LENGTH_SHORT).show()
                                    }
                                }else {
                                    Toast.makeText(this, "Escriba sus Apellidos", Toast.LENGTH_SHORT).show()
                                }
                            }else{
                                Toast.makeText(this, "Escriba sus Nombres", Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            Toast.makeText(this, "las contraseñas debe ser iguales", Toast.LENGTH_SHORT).show()
                        }
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
        */
        //register()
    }
    // funcion que ejecuta el registro al oprimir el boton
    /* private fun register() {
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

         }*/

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

    private fun loadPolicyFragment(fragmentPolicy: Fragment) {
        var manag: FragmentManager = supportFragmentManager
        var transac: FragmentTransaction = manag.beginTransaction()
        transac.replace(R.id.fragment_policy, fragmentPolicy)
        transac.commit()
    }

    fun onClickBtnOkPolicy(view: android.view.View) {
        linlay_policy_container.isVisible = false
        linlay_policy_container.isEnabled = false
        buttonRegisterRegister.isEnabled = true
        if (cbxAcceptPolicy){
            //buttonRegisterRegister.isEnabled = true

        }else {
            Toast.makeText(this, "Please accept the privacy policy.", Toast.LENGTH_SHORT).show()
        }
    }

    fun onClickCbxAcceptPolicy(view: android.view.View) {
        cbxAcceptPolicy = !cbxAcceptPolicy
    }

}