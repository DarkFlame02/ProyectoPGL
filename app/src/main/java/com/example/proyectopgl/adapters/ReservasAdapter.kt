package com.example.proyectopgl.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectopgl.R

class ReservasAdapter(private val listaNombres: MutableList<String>, private val listaFechas: MutableList<String>, private val listaHoras: MutableList<String>, private val imagenes: List<Int>, private val onItemClick: (Int) -> Unit, private val onLongClick: (Int) -> Unit) : RecyclerView.Adapter<ReservasAdapter.ReservaViewHolder>() {

    class ReservaViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val card: CardView = item.findViewById(R.id.reservasCardView)
        val nombreReserva: TextView = item.findViewById(R.id.nombreReservaText)
        val fechaReserva: TextView = item.findViewById(R.id.fechaReservaText)
        val horaReserva: TextView = item.findViewById(R.id.horaReservaText)
        val imagen: ImageView = item.findViewById(R.id.reservaImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservaViewHolder {
        val reservaCardView = LayoutInflater.from(parent.context)
        return ReservaViewHolder(reservaCardView.inflate(R.layout.reservas_card_view, parent, false))
    }

    override fun getItemCount(): Int = listaNombres.size

    override fun onBindViewHolder(holder: ReservaViewHolder, position: Int) {
        holder.nombreReserva.text = listaNombres[position]
        holder.fechaReserva.text = listaFechas[position]
        holder.horaReserva.text = listaHoras[position]
        holder.imagen.setImageResource(imagenes[position])

        holder.card.setOnClickListener { onItemClick(position) }

        holder.card.setOnLongClickListener {
            onLongClick(position)
            true
        }
    }


}