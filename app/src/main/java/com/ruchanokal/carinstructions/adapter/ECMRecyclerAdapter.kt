package com.ruchanokal.carinstructions.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ruchanokal.carinstructions.databinding.EcmRowBinding
import com.ruchanokal.carinstructions.fragments.ECMFragmentDirections
import com.ruchanokal.carinstructions.fragments.MainFragmentDirections
import com.ruchanokal.carinstructions.model.ECM

class ECMRecyclerAdapter(val ecmList : ArrayList<ECM>, val lang : String)
    : RecyclerView.Adapter<ECMRecyclerAdapter.ECMHolder>() {

    class ECMHolder(val binding: EcmRowBinding) : RecyclerView.ViewHolder(binding.root) {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ECMHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = EcmRowBinding.inflate(inflater,parent,false)
        return ECMHolder(view)
    }

    override fun onBindViewHolder(holder: ECMHolder, position: Int) {

        holder.binding.markaText.text = ecmList.get(position).marka
        holder.binding.aracModeliText.text = ecmList.get(position).aracmodeli
        holder.binding.aracTipiText.text = ecmList.get(position).aractipi
        holder.binding.ecmText.text = ecmList.get(position).ecm

        holder.itemView.setOnClickListener {

            val action = ECMFragmentDirections.actionECMFragmentToECMDetailsFragment(ecmList.get(position),lang)
            Navigation.findNavController(it).navigate(action)

        }

    }

    override fun getItemCount(): Int {
        return ecmList.size
    }
}