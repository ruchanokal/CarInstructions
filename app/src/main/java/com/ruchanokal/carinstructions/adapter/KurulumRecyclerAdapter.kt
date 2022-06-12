package com.ruchanokal.carinstructions.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ruchanokal.carinstructions.databinding.KurulumRowBinding
import com.ruchanokal.carinstructions.model.OnTextClickListener


class KurulumRecyclerAdapter(val markaKurulumList : ArrayList<String>, val listener: OnTextClickListener): RecyclerView.Adapter<KurulumRecyclerAdapter.KurulumHolder>() {


    private val tag = "KurulumRecyclerAdapter"
    //private val savedArrayList = arrayListOf<String>()


    class KurulumHolder(val binding: KurulumRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }


    override fun getItemCount(): Int {
        return markaKurulumList.size
    }

    override fun onBindViewHolder(holder: KurulumHolder, position: Int) {

        holder.binding.checkboxKurulum.text = markaKurulumList.get(position)

        holder.binding.checkboxKurulum.setOnCheckedChangeListener { compoundButton, b ->

            if (b){
                listener?.onTextClick(compoundButton.text.toString(),b)
            }else
                listener?.onTextClick(compoundButton.text.toString(),b)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KurulumHolder {
        val binding = KurulumRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return KurulumHolder(binding)
    }
}