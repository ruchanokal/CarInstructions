package com.ruchanokal.carinstructions.adapter

import android.R
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ruchanokal.carinstructions.databinding.MainRecyclerViewRowBinding
import com.ruchanokal.carinstructions.fragments.MainFragmentDirections


class MainRecyclerAdapter(val markaList : ArrayList<String>, val lang: String) : RecyclerView.Adapter<MainRecyclerAdapter.MainHolder>() {


    class MainHolder(val binding: MainRecyclerViewRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerAdapter.MainHolder {

        val binding = MainRecyclerViewRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MainHolder(binding)

    }

    override fun onBindViewHolder(holder: MainRecyclerAdapter.MainHolder, position: Int) {

        holder.binding.mainButton.text = markaList.get(position)

        holder.binding.mainButton.setOnClickListener {

            val action = MainFragmentDirections.actionMainFragmentToTalimatFragment(markaList.get(position),lang)
            Navigation.findNavController(it).navigate(action)


        }

    }

    override fun getItemCount(): Int {
        return markaList.size
    }
}