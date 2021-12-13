package com.example.run

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ItemListAdapter(context: AppCompatActivity,
                      val info: Bundle): RecyclerView.Adapter<ItemListAdapter.MyViewHolder>(){

    class MyViewHolder(val layout: View): RecyclerView.ViewHolder(layout)

    private var context: AppCompatActivity = context

    var bikeList: BikeList = info.getParcelable("products")!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        var layout = LayoutInflater.from(parent.context).inflate(R.layout.bike_list_items, parent, false)
//        return MyViewHolder(layout)
        var layout = LayoutInflater.from(parent.context).inflate(R.layout.bike_card_view_grid, parent, false)
        return MyViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var textViewBikeName = holder.layout.findViewById<TextView>(R.id.textViewItem2)
        textViewBikeName.text = bikeList.bikes[position].nombre

        var textViewBikeMake = holder.layout.findViewById<TextView>(R.id.textViewMake2)
        textViewBikeMake.text = bikeList.bikes[position].marca

        var textViewBikeId = holder.layout.findViewById<TextView>(R.id.textViewBikeId)
        textViewBikeId.text = bikeList.bikes[position].id

        var imgViewBike = holder.layout.findViewById<ImageView>(R.id.imageViewBike2)
        Glide
            .with(holder.itemView.context)
            .load(bikeList.bikes[position].imgUrl)
            .into(imgViewBike)

        holder.layout.setOnClickListener{
            // Toast.makeText(holder.itemView.context, textViewBikeName.text, Toast.LENGTH_LONG).show()
            //db.collection("MotosTest").document(textViewBikeId.text).get()

            var bikeId: Int = textViewBikeId.text.toString().toInt()
            var bike= bikeList.bikes[bikeId]
            var info: Bundle = Bundle()
            info.putParcelable("bikeSelected", bike)

            val intent = Intent(context, BikeDetailActivity::class.java)
            intent.putExtra("bikeSelected",info)
            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return bikeList.bikes.size
    }

}