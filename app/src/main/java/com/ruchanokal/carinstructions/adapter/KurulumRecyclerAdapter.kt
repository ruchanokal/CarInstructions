package com.ruchanokal.carinstructions.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ruchanokal.carinstructions.databinding.KurulumRowBinding
import com.ruchanokal.carinstructions.model.OnTextClickListener


class KurulumRecyclerAdapter(val markaKurulumNameList : ArrayList<String>,
                             val markaKurulumImageList : ArrayList<String>,
                             val listener: OnTextClickListener)
    : RecyclerView.Adapter<KurulumRecyclerAdapter.KurulumHolder>() {


    private val TAG = "KurulumRecyclerAdapter"


    class KurulumHolder(val binding: KurulumRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }


    override fun getItemCount(): Int {
        return markaKurulumNameList.size
    }

    override fun onBindViewHolder(holder: KurulumHolder, position: Int) {


        Log.i(TAG,"name " + position + "  "  + markaKurulumNameList.get(position))
        Log.i(TAG,"image " + position + "  " + markaKurulumImageList.get(position))

        holder.binding.checkboxKurulum.text = markaKurulumNameList.get(position)

        holder.binding.checkboxKurulum.setOnCheckedChangeListener { compoundButton, b ->

           listener.onTextClick(compoundButton.text.toString(),markaKurulumImageList.get(position),b)

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KurulumHolder {
        val binding = KurulumRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return KurulumHolder(binding)
    }
}