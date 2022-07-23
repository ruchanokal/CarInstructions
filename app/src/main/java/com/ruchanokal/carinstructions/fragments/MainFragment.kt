package com.ruchanokal.carinstructions.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.ruchanokal.carinstructions.R
import com.ruchanokal.carinstructions.adapter.MainRecyclerAdapter
import com.ruchanokal.carinstructions.databinding.FragmentMainBinding
import com.ruchanokal.carinstructions.model.TinyDB
import java.util.*
import kotlin.collections.ArrayList


class MainFragment : Fragment() {


    private var binding: FragmentMainBinding? = null

    val markaArrayList : ArrayList<String> = ArrayList()
    private lateinit var db : FirebaseFirestore
    var adapter : MainRecyclerAdapter? = null

    private val TAG = "MainFragment"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val prefences = requireActivity().getSharedPreferences("com.ruchanokal.carinstructions", Context.MODE_PRIVATE)
        val lang = prefences.getString("lang","tr")

        val tinyDB = TinyDB(requireContext())

        arguments?.let {


            val savedNameList = MainFragmentArgs.fromBundle(it).savedNamelist.toList()
            val savedImageList = MainFragmentArgs.fromBundle(it).savedImageList.toList()


            binding!!.recyclerViewMain.layoutManager = LinearLayoutManager(requireContext())
            adapter = MainRecyclerAdapter(savedNameList as ArrayList<String>,
                savedImageList as ArrayList<String>,lang!!)
            binding!!.recyclerViewMain.adapter = adapter
            tinyDB.putListString("savedNameArrayList",savedNameList)
            tinyDB.putListString("savedImageArrayList",savedImageList)

            prefences.edit().putBoolean("control",true).apply()

            Log.i("MainFragment","control true")

        }

        //db = FirebaseFirestore.getInstance()

        //getDataFromFirestore()



    }

    private fun getDataFromFirestore() {

        db.collection("Markalar").addSnapshotListener { value, error ->

            if (error == null) {

                if ( value != null) {

                    if ( !value.isEmpty ) {

                        markaArrayList.clear()

                        val documents = value.documents
                        for (document in documents) {
                            val name = document.get("name") as String?
                            if (name != null) {
                                markaArrayList.add(name)
                            }
                        }
                        adapter!!.notifyDataSetChanged()

                    }

                }

            } else
                Toast.makeText(requireContext(),"Bir hata oluştu -->> " + error.localizedMessage,Toast.LENGTH_SHORT).show()
            
        }

    }

}