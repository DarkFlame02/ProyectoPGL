package com.example.proyectopgl.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectopgl.R

class ReservasAdapter(private val diaReserva: List<String>, private val fechaReserva: List<String>, private val horaReserva: List<String>, private val onItemClick: (Int) -> Unit) : RecyclerView.Adapter<ReservasAdapter.ReservaViewHolder>() {

    class ReservaViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val card: CardView = item.findViewById(R.id.reservasCardView)
        val diaReserva: TextView = item.findViewById(R.id.diaReservaText)
        val fechaReserva: TextView = item.findViewById(R.id.fechaReservaText)
        val horaReserva: TextView = item.findViewById(R.id.horaReservaText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservaViewHolder {
        val reservaCardView = LayoutInflater.from(parent.context)
        return ReservaViewHolder(reservaCardView.inflate(R.layout.reservas_card_view, parent, false))
    }

    override fun getItemCount(): Int = diaReserva.size

    override fun onBindViewHolder(holder: ReservaViewHolder, position: Int) {
        holder.diaReserva.text = diaReserva[position]
        holder.fechaReserva.text = fechaReserva[position]
        holder.horaReserva.text = horaReserva[position]
        holder.card.setOnClickListener { onItemClick(position) }
    }

}