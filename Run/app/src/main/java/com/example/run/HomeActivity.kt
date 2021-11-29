package com.example.run

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat.*
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.content_home.*

class HomeActivity() : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var auth: FirebaseAuth

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

        /**val menuItem = navigationView.menu.getItem(0);
        onNavigationItemSelected(menuItem);
        menuItem.isChecked = true;

        drawerLayout.addDrawerListener(this)

        val header = navigationView.getHeaderView(0)
        header.findViewById<View>(R.id.nav_header).setOnClickListener {
            Toast.makeText(
                this@HomeActivity, getString(R.string.),
                Toast.LENGTH_SHORT
            ).show()
        }**/

        //-------------------- content_home----------------------------------------------------
        // auth = FirebaseAuth.getInstance();
        button.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }
        //-------------------------------------------------------------------------------------
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
        drawerLayout.closeDrawer(START)
        return false
    }

    /**override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }**/
}

