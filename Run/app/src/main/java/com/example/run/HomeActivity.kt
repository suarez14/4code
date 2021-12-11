package com.example.run

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestore.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.nav_header.view.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
//class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var database:DatabaseReference
    private lateinit var auth: FirebaseAuth

    val db= FirebaseFirestore.getInstance()
    //database = firebase.database.reference
    //val buttonLogout = findViewById<Button>(R.id.buttonHomeLogout)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar= findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setTitle(R.string.title_home)
        supportActionBar?.setSubtitle(R.string.app_name)

        val drawerLayout = findViewById<DrawerLayout>(R.id.activity_home)


        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        db.collection("users").get().addOnSuccessListener {
            getUserProfile()
            //getProviderData()
        }


        /*buttonLoginLogin.setOnClickListener{
            //db.collection("users").get().addOnSuccessListener {
                //user_name.setText(document.getString("firstName").toString()!!)
                //user_email.setText(document.getString("email").toString()!!)
            //}
        }*/

        button.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
    //Metodo para obtener los datos del perfil de usuario
    private fun getUserProfile(){

        val user = Firebase.auth.currentUser
        user?.let {
            //Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            //val photoUrl = user.photoUrl

            //Check if user's email is verified
            val emailVerified = user.isEmailVerified
            //
            val uid= user.uid
            db.collection("users").get().addOnSuccessListener { result ->
                for (document in result) {
                    if (document.get("email").toString().equals(user?.email))
                        user_name.setText(document.get("firstName").toString())
                }
            }
        }
        user_name.setText(user?.displayName)
        user_email.setText(user?.email)
    }

    //Metodo para obtener los datos del perfil de usuario desde una cuenta google
    private fun getProviderData(){

        val user = Firebase.auth.currentUser
        user?.let {
            for (profile in it.providerData){
                var providerId = profile.providerId
                var uid = profile.uid
                val name = user.displayName
                val email = user.email
            }
        }
        db.collection("users").get().addOnSuccessListener { result ->
            for (document in result) {
                if (document.get("email").toString().equals(user?.email))
                    user_name.setText(document.get("firstName").toString())
            }
        }
        //user_name.setText(user?.displayName)
        user_email.setText(user?.email)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.op1Nav -> textView.text = "Perferences"
            R.id.op2Nav ->  {   Firebase.auth.signOut()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.op3Nav  -> textView.text = "User Date"
            else -> textView.text = "Home"
        }

        val drawerLayout = findViewById<DrawerLayout>(R.id.activity_home)
        drawerLayout.closeDrawer(GravityCompat.START)
        return false
    }
}