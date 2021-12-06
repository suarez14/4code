package com.example.run

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
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

        var myBikeNames: ArrayList<String> = ArrayList()
//        myBikeNames.add("CB 175")
//        myBikeNames.add("Vespa 150")
//        myBikeNames.add("HD 450")
//        myBikeNames.add("CB 175")
//        myBikeNames.add("Vespa 150")
//        myBikeNames.add("HD 450")
//        myBikeNames.add("CB 175")
//        myBikeNames.add("Vespa 150")
//        myBikeNames.add("HD 450")

        var myBikeMakes: ArrayList<String> = ArrayList()
//        myBikeMakes.add("Honda")
//        myBikeMakes.add("Piaggio")
//        myBikeMakes.add("Harley Davison")
//        myBikeMakes.add("Honda")
//        myBikeMakes.add("Piaggio")
//        myBikeMakes.add("Harley Davison")
//        myBikeMakes.add("Honda")
//        myBikeMakes.add("Piaggio")
//        myBikeMakes.add("Harley Davison")

        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    myBikeNames.add(document.get("firstName").toString())
                    myBikeMakes.add(document.get("email").toString())
                }
                var info: Bundle = Bundle()
                info.putStringArrayList("names", myBikeNames)
                info.putStringArrayList("makes", myBikeMakes)

                listRecyclerView = requireView().findViewById(R.id.recyclerItemsList)
                myAdapter = ItemListAdapter(activity as AppCompatActivity, info)

                listRecyclerView.setHasFixedSize(true)
                listRecyclerView.adapter = myAdapter
                listRecyclerView.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
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