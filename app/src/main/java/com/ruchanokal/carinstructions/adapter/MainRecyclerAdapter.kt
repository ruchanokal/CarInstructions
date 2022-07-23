package com.ruchanokal.carinstructions.adapter

import android.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ruchanokal.carinstructions.databinding.MainRecyclerViewRowBinding
import com.ruchanokal.carinstructions.fragments.MainFragmentDirections


class MainRecyclerAdapter(val markaNameList : ArrayList<String>,
                          val markaImageList : ArrayList<String>,
                          val lang: String) : RecyclerView.Adapter<MainRecyclerAdapter.MainHolder>() {


    class MainHolder(val binding: MainRecyclerViewRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerAdapter.MainHolder {

        val binding = MainRecyclerViewRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MainHolder(binding)

    }

    override fun onBindViewHolder(holder: MainRecyclerAdapter.MainHolder, position: Int) {

        holder.binding.markaName.text = markaNameList.get(position)
        Glide.with(holder.itemView.context).load(markaImageList.get(position)).into(holder.binding.markaImage)

        holder.binding.mainButton.setOnClickListener {

            val action = MainFragmentDirections.actionMainFragmentToECMFragment(markaNameList.get(position),lang)
            Navigation.findNavController(it).navigate(action)


        }

    }

    override fun getItemCount(): Int {
        return markaNameList.size
    }
}