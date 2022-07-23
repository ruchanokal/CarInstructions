package com.ruchanokal.carinstructions.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ruchanokal.carinstructions.R
import com.ruchanokal.carinstructions.adapter.ECMRecyclerAdapter
import com.ruchanokal.carinstructions.adapter.MainRecyclerAdapter
import com.ruchanokal.carinstructions.databinding.FragmentEcmBinding
import com.ruchanokal.carinstructions.model.ECM

class ECMFragment : Fragment() {

    private var binding: FragmentEcmBinding? = null
    private lateinit var db : FirebaseFirestore
    val ecmList : ArrayList<ECM> = ArrayList()
    var adapter : ECMRecyclerAdapter? = null
    private val TAG = "ECM_Fragment"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEcmBinding.inflate(inflater, container, false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = Firebase.firestore

        arguments?.let {

            val markaName = ECMFragmentArgs.fromBundle(it).name
            val lang = ECMFragmentArgs.fromBundle(it).lang

            binding!!.ecmRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            adapter = ECMRecyclerAdapter(ecmList,lang)
            binding!!.ecmRecyclerView.adapter = adapter

            db.collection("ECM").whereEqualTo("marka",markaName)
                .addSnapshotListener { value, error ->

                if (error == null) {

                    if (value != null) {

                        val documents = value.documents
                        ecmList.clear()

                        for (document in documents) {

                            val aracmodeli = document.getString("aracmodeli")
                            val aractipi = document.getString("aractipi")
                            val ecm = document.getString("ecm")
                            val marka = document.getString("marka")
                            val url = document.getString("url")

                            val ecmModel = ECM(marka,ecm,aractipi,aracmodeli,url)
                            ecmList.add(ecmModel)

                        }

                        /*
                        ecmList.sortWith(Comparator { lhs, rhs ->
                            // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                            if (lhs.aractipi!! < rhs.aractipi!!){
                                Log.i(TAG,"less than -->> " + lhs.aractipi + " " + rhs.aractipi )
                                -1
                            }
                            else if (lhs.aracmodeli!! < rhs.aracmodeli!!){
                                1
                                Log.i(TAG,"greater than: " + lhs.aracmodeli + " " + rhs.aracmodeli)
                            }
                            else {
                                0
                                Log.i(TAG,"equals" + lhs.aracmodeli + " " + rhs.aracmodeli)
                            }
                        })
                        */

                        ecmList.sortWith(compareBy({ it.aractipi},{it.aracmodeli}))

                        adapter!!.notifyDataSetChanged()

                    }

                } else {

                    Toast.makeText(requireContext(),"The error is " + error,Toast.LENGTH_SHORT).show()
                }



            }


        }


    }
}