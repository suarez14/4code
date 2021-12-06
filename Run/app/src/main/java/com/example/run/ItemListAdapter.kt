package com.example.run

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class ItemListAdapter(context: AppCompatActivity
                      , val info: Bundle): RecyclerView.Adapter<ItemListAdapter.MyViewHolder>(){

    class MyViewHolder(val layout: View): RecyclerView.ViewHolder(layout)

    private var context: AppCompatActivity = context

    var myBikeNames: ArrayList<String> = info.getStringArrayList("names") as ArrayList<String>
    var myBikeMakes: ArrayList<String> = info.getStringArrayList("makes") as ArrayList<String>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var layout = LayoutInflater.from(parent.context).inflate(R.layout.bike_list_items, parent, false)
        return MyViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var textViewBikeName = holder.layout.findViewById<TextView>(R.id.textViewItem)
        textViewBikeName.text = myBikeNames[position]

        var textViewBikeMake = holder.layout.findViewById<TextView>(R.id.textViewMake)
        textViewBikeMake.text = myBikeMakes[position]

        holder.layout.setOnClickListener{
            Toast.makeText(holder.itemView.context, textViewBikeName.text, Toast.LENGTH_LONG).show()
        }
    }

    override fun getItemCount(): Int {
        return myBikeNames.size
    }

}