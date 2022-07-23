package com.ruchanokal.carinstructions.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ruchanokal.carinstructions.databinding.FragmentEcmDetailsBinding


class ECMDetailsFragment : Fragment() {

    var binding : FragmentEcmDetailsBinding? = null
    private lateinit var db : FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEcmDetailsBinding.inflate(inflater,container,false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = Firebase.firestore

        arguments?.let {

            val ecm = ECMDetailsFragmentArgs.fromBundle(it).ecmModel
            val lang = ECMDetailsFragmentArgs.fromBundle(it).lang

            Glide.with(requireContext()).load(ecm.url).into(binding!!.ecmImageView)

            binding!!.ecmName.text = ecm.ecm

            ecm.ecm?.let { it ->

                db.collection("Parca").document(it).addSnapshotListener { value, error ->

                    if (error == null) {

                        if (value != null) {

                            if (value.exists()) {

                                val dtb = value["dtb"] as String?
                                val egk = value["egk"] as String?
                                val ecuens = value["ecuens"] as String?
                                val anaens = value["anaens"] as String?
                                val kp = value["kp"] as String?
                                val kpge = value["kpge"] as String?

                                binding!!.kontrolPaneli.text = kp
                                binding!!.dataTransferBox.text = dtb
                                binding!!.ecuEnstelasyon.text = ecuens
                                binding!!.anaEnstelasyon.text = anaens
                                binding!!.kontrolPaneliGucEnstelasyon.text = kpge

                                if (egk != null) {

                                    binding!!.egkLayout.visibility = View.VISIBLE
                                    binding!!.ecuGucKonnektoru.text = egk

                                } else {

                                    binding!!.egkLayout.visibility = View.GONE

                                }


                            }
                        }

                    } else {

                        Toast.makeText(requireContext(),"The error -- " + error,Toast.LENGTH_LONG).show()

                    }


                }


                binding!!.devamButton.setOnClickListener {

                    val action = ECMDetailsFragmentDirections.actionECMDetailsFragmentToTalimatFragment(lang,ecm.ecm)
                    Navigation.findNavController(it).navigate(action)


                }


            }






        }

    }
}