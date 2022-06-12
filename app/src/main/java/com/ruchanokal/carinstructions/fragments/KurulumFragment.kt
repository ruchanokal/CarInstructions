package com.ruchanokal.carinstructions.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.ruchanokal.carinstructions.R
import com.ruchanokal.carinstructions.activities.MainActivity
import com.ruchanokal.carinstructions.adapter.KurulumRecyclerAdapter
import com.ruchanokal.carinstructions.databinding.FragmentKurulumBinding
import com.ruchanokal.carinstructions.model.OnTextClickListener
import com.ruchanokal.carinstructions.model.TinyDB


class KurulumFragment : Fragment(),OnTextClickListener {

    private var binding: FragmentKurulumBinding? = null
    private val TAG = "KurulumFragment"

    val markaKurulumArrayList : ArrayList<String> = ArrayList()
    var savedArrayList = arrayListOf<String>()

    private lateinit var db : FirebaseFirestore
    var adapter : KurulumRecyclerAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKurulumBinding.inflate(inflater, container, false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefences = requireActivity().getSharedPreferences("com.ruchanokal.carinstructions", Context.MODE_PRIVATE)
        val control = prefences.getBoolean("control",false)

        Log.i(TAG,"control: " + control)

        if (control) {

            val tinyDB = TinyDB(requireContext())
            savedArrayList = tinyDB.getListString("savedList")

            val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            val navController = navHostFragment.navController

            val action = KurulumFragmentDirections
                .actionKurulumFragmentToMainFragment(savedArrayList.toTypedArray())
            navController.navigate(action)
            return

        }
        Log.i(TAG,"after control")


        db = FirebaseFirestore.getInstance()

        getDataFromFirestore()



        binding!!.recyclerViewKurulum.layoutManager = LinearLayoutManager(requireContext())
        adapter = KurulumRecyclerAdapter(markaKurulumArrayList,this)
        binding!!.recyclerViewKurulum.adapter = adapter

        binding!!.kurulumButton.setOnClickListener {

            val action = KurulumFragmentDirections
                .actionKurulumFragmentToMainFragment(savedArrayList.toTypedArray())
            Navigation.findNavController(it).navigate(action)

        }

    }

    private fun getDataFromFirestore() {



        db.collection("Markalar").addSnapshotListener { value, error ->

            if (error == null) {

                if ( value != null) {

                    if ( !value.isEmpty ) {

                        markaKurulumArrayList.clear()

                        val documents = value.documents
                        for (document in documents) {
                            val name = document.get("name") as String?
                            if (name != null) {
                                markaKurulumArrayList.add(name)
                            }
                        }
                        adapter!!.notifyDataSetChanged()

                    }

                }

            } else {
                Toast.makeText(requireContext(),"Bir hata oluştu -->> " + error.localizedMessage,
                    Toast.LENGTH_SHORT).show()
            }


        }

    }

    override fun onTextClick(name: String, isChecked: Boolean) {
        Log.i(TAG,"name: " +name)
        Log.i(TAG,"is: " +isChecked)

        if (isChecked){
            savedArrayList.add(name)
            Log.i(TAG,"savedArrayList-1: " + savedArrayList)
        } else {
            savedArrayList.remove(name)
            Log.i(TAG,"savedArrayList-2: " + savedArrayList)
        }

        val set: Set<String> = HashSet(savedArrayList)
        savedArrayList.clear()
        savedArrayList.addAll(set)

        Log.i(TAG,"savedArrayList-son: " + savedArrayList)

    }


}