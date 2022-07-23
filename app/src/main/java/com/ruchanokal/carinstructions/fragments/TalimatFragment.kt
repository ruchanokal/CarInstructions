package com.ruchanokal.carinstructions.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ruchanokal.carinstructions.R
import com.ruchanokal.carinstructions.adapter.ViewPagerAdapter
import com.ruchanokal.carinstructions.databinding.FragmentTalimatlarBinding
import com.ruchanokal.carinstructions.model.PagerModel


class TalimatFragment : Fragment() {

    private var binding: FragmentTalimatlarBinding? = null
    lateinit var viewPagerAdapter: PagerAdapter
    var mList = arrayListOf<PagerModel>()

    private lateinit var db : FirebaseFirestore


    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentTalimatlarBinding.inflate(inflater, container, false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = Firebase.firestore

        arguments?.let {

            val name = TalimatFragmentArgs.fromBundle(it).ecm
            val lang = TalimatFragmentArgs.fromBundle(it).lang


            if (lang.equals("tr")) {

                db.collection("Talimatlar").document(name).addSnapshotListener { value, e ->

                    if (e == null) {

                        if ( value != null) {

                            if ( value.exists() ) {

                                mList.clear()

                                val talimatSayisi = value["ts"] as Long

                                for (x in 1..talimatSayisi) {

                                    val talimatText = value["talimat"+x] as String?
                                    val talimatImage = value["foto"+x] as String?

                                    val item = talimatText?.let { it1 ->
                                        if (talimatImage != null) {
                                            PagerModel(it1,talimatImage)
                                        } else {
                                            PagerModel(it1,"")
                                        }
                                    }

                                    if (item != null) {
                                        mList.add(item)
                                    }


                                }

                                if (requireContext() != null)
                                    viewPagerAdapter = ViewPagerAdapter(requireContext(),mList)


                                binding?.screenPager?.adapter = viewPagerAdapter
                                binding?.tablayout?.setupWithViewPager(binding?.screenPager)

                            }

                        }

                    } else {

                        Toast.makeText(requireContext(),"Hata: " + e,Toast.LENGTH_SHORT).show()
                    }

                }


            } else {

                db.collection("TalimatlarEng").document(name).addSnapshotListener { value, e ->

                    if (e == null) {

                        if ( value != null) {

                            if ( value.exists() ) {

                                mList.clear()

                                val talimatSayisi = value["ts"] as Long

                                for (x in 1..talimatSayisi) {

                                    val talimatText = value["talimat"+x] as String?
                                    val talimatImage = value["foto"+x] as String?

                                    val item = talimatText?.let { it1 ->
                                        if (talimatImage != null) {
                                            PagerModel(it1,talimatImage)
                                        } else {
                                            PagerModel(it1,"")
                                        }
                                    }

                                    if (item != null) {
                                        mList.add(item)
                                    }


                                }

                                if (requireContext() != null)
                                    viewPagerAdapter = ViewPagerAdapter(requireContext(),mList)


                                binding?.screenPager?.adapter = viewPagerAdapter
                                binding?.tablayout?.setupWithViewPager(binding?.screenPager)

                            }

                        }

                    } else {

                        Toast.makeText(requireContext(),"Hata: " + e,Toast.LENGTH_SHORT).show()
                    }

                }


            }




        }


    }
}