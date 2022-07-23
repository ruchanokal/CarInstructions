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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.ruchanokal.carinstructions.R
import com.ruchanokal.carinstructions.activities.MainActivity
import com.ruchanokal.carinstructions.adapter.KurulumRecyclerAdapter
import com.ruchanokal.carinstructions.databinding.FragmentKurulumBinding
import com.ruchanokal.carinstructions.model.OnTextClickListener
import com.ruchanokal.carinstructions.model.TinyDB


class KurulumFragment : Fragment(),OnTextClickListener {

    private var binding: FragmentKurulumBinding? = null
    private val TAG = "KurulumFragment"

    val markaKurulumNameList : ArrayList<String> = ArrayList()
    val markaKurulumImageList : ArrayList<String> = ArrayList()

    var savedNameArrayList = arrayListOf<String>()
    var savedImageArrayList = arrayListOf<String>()

    private lateinit var db : FirebaseFirestore
    var adapter : KurulumRecyclerAdapter? = null
    private lateinit var auth: FirebaseAuth


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
            savedNameArrayList = tinyDB.getListString("savedNameArrayList")
            savedImageArrayList = tinyDB.getListString("savedImageArrayList")

            val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            val navController = navHostFragment.navController

            val action = KurulumFragmentDirections
                .actionKurulumFragmentToMainFragment(savedNameArrayList.toTypedArray()
                    ,savedImageArrayList.toTypedArray())
            navController.navigate(action)
            return

        }
        Log.i(TAG,"after control")

        auth = Firebase.auth

        auth.signInAnonymously()
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInAnonymously:success")

                    db = FirebaseFirestore.getInstance()
                    getDataFromFirestore()

                    binding!!.recyclerViewKurulum.layoutManager = LinearLayoutManager(requireContext())
                    adapter = KurulumRecyclerAdapter(markaKurulumNameList, markaKurulumImageList,this)
                    binding!!.recyclerViewKurulum.adapter = adapter

                    binding!!.mainProgressBar.visibility = View.GONE

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInAnonymously:failure", task.exception)

                }
            }


        binding!!.kurulumButton.setOnClickListener {

            val savedNameArray = savedNameArrayList.toTypedArray()
            val savedImageArray = savedImageArrayList.toTypedArray()

            if (savedNameArray.size > 0 ) {

                val action = KurulumFragmentDirections
                    .actionKurulumFragmentToMainFragment(savedNameArray,savedImageArray)
                Navigation.findNavController(it).navigate(action)

            } else {

                Toast.makeText(requireContext(),resources.getString(R.string.en_az_bir_marka),Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun getDataFromFirestore() {



        db.collection("Markalar").addSnapshotListener { value, error ->

            if (error == null) {

                if ( value != null) {

                    if ( !value.isEmpty ) {

                        markaKurulumNameList.clear()
                        markaKurulumImageList.clear()

                        val documents = value.documents
                        for (document in documents) {
                            val name = document.get("name") as String?
                            val image = document.get("image") as String?
                            if (name != null) {
                                markaKurulumNameList.add(name)
                                Log.i(TAG,"markaKurulumName: " + name)
                            } else
                                markaKurulumNameList.add("")

                            if (image != null) {
                                markaKurulumImageList.add(image)
                                Log.i(TAG,"markaKurulumImage: " + image)
                            } else
                                markaKurulumImageList.add("")

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

    override fun onTextClick(name: String, url : String, isChecked: Boolean) {
        Log.i(TAG,"name: " +name)
        Log.i(TAG,"isChecked: " +isChecked)

        if (isChecked){
            savedNameArrayList.add(name)
            savedImageArrayList.add(url)
            Log.i(TAG,"savedNameArrayList-1: " + savedNameArrayList)
            Log.i(TAG,"savedImageArrayList-1: " + savedImageArrayList)

        } else {
            savedNameArrayList.remove(name)
            savedImageArrayList.remove(url)

            Log.i(TAG,"savedNameArrayList-2: " + savedNameArrayList)
            Log.i(TAG,"savedImageArrayList-2: " + savedImageArrayList)

        }

        Log.i(TAG,"savedNameArrayList-son: " + savedNameArrayList)
        Log.i(TAG,"savedImageArrayList-son: " + savedImageArrayList)

    }


}