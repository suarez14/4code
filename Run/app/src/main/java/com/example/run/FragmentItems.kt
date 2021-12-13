package com.example.run

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentItems.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentItems : Fragment() {

    private lateinit var listRecyclerView: RecyclerView
    private lateinit var myAdapter: RecyclerView.Adapter<ItemListAdapter.MyViewHolder>

    private lateinit var db:FirebaseFirestore
    private lateinit var products: BikeList
    private lateinit var productsList: MutableList<Bike>

    var TAG = ""

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        db = FirebaseFirestore.getInstance()
        productsList = ArrayList<Bike>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragment: View = inflater.inflate(R.layout.fragment_items, container, false)
        return fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db.collection("MotosTest")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var bike:Bike = Bike(document.id,
                                         document.getString("Nombre")!!,
                                         document.getString("Marca")!!,
                                         document.getLong("Modelo")!!.toInt(),
                                         document.getLong("Kilometraje")!!.toInt(),
                                         document.getString("Cilindraje")!!,
                                         document.getLong("Precio")!!.toInt(),
                                         document.getString("URLFoto")!!)
                    productsList.add(bike)
                }
                products = BikeList(productsList)
                var info: Bundle = Bundle()
                info.putParcelable("products", products)
                listRecyclerView = requireView().findViewById(R.id.recyclerItemsList)
                myAdapter = ItemListAdapter(activity as AppCompatActivity, info)

                listRecyclerView.setHasFixedSize(true)
//                val layoutManagerLin = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
//                listRecyclerView.layoutManager = layoutManagerLin
                val layoutManagerGrid = GridLayoutManager(context,2)
                listRecyclerView.layoutManager = layoutManagerGrid
                listRecyclerView.adapter = myAdapter
//                listRecyclerView.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.")
                Toast.makeText(context, "Error getting documents.", Toast.LENGTH_LONG).show()
            }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentItems.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentItems().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}