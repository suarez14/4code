package com.example.run

import android.app.ActionBar
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_bike_detail.*
import kotlinx.android.synthetic.main.activity_register.*

class BikeDetailActivity : AppCompatActivity() {

    val db= FirebaseFirestore.getInstance()




    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bike_detail)



        getInfoMoto()
        // funcion que lleva a login  con el boton atrás
        buttonAtras.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }




    }



    //Metodo para obtener los datos de la moto
    private fun getInfoMoto(){

        var intent: Intent
        intent = getIntent()
        var bundleBike:Bundle = intent.getBundleExtra("bikeSelected")!!

        var bike: Bike = bundleBike.getParcelable("bikeSelected")!!



        textViewNombre.setText("Nombre: "+ bike.nombre)
        textViewCC.setText("Cilindraje: "+ bike.cilindraje)
        textKilometraje.setText("Kilometraje: "+ bike.kms.toString() + "Kms")
        textViewModelo.setText("Modelo: "+ bike.modelo.toString())
        textViewMarca.setText("Marca: "+ bike.marca)
        textViewPrecio.setText("Precio: "+ bike.precio.toString() + "COP")



        Glide
            .with(this)
            .load(bike.imgUrl)
            .into(imageViewBike4)


    }
}