package com.example.proyectopgl.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectopgl.R

class BonoAdapter(private val nombreBonos: List<String>, private val descBonos: List<String>, private val imagenBonos: Array<Int>, private val onItemClick: (String, String, Int) -> Unit) : RecyclerView.Adapter<BonoAdapter.BonoViewHolder>() {

    class BonoViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val card: CardView = item.findViewById(R.id.bonosCardView)
        val bonoNombre: TextView = item.findViewById(R.id.bonosTitle)
        val bonoDesc: TextView = item.findViewById(R.id.bonosDesc)
        val bonoImage: ImageView = item.findViewById(R.id.bonosImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BonoViewHolder {
        val bonosCardView = LayoutInflater.from(parent.context)
        return BonoViewHolder(bonosCardView.inflate(R.layout.bonos_card_view, parent, false))
    }

    override fun getItemCount(): Int = nombreBonos.size

    override fun onBindViewHolder(holder: BonoViewHolder, position: Int) {
        holder.bonoNombre.text = nombreBonos[position]
        holder.bonoDesc.text = descBonos[position]
        holder.bonoImage.setImageResource(imagenBonos[position])

        holder.card.setOnClickListener {
            onItemClick(nombreBonos[position], descBonos[position], imagenBonos[position]) }
    }

}