package com.example.proyectopgl.datos

object ReservaRepository {
    private val reservas = mutableListOf<Reserva>()

    fun addReserva(reserva: Reserva) {
        reservas.add(reserva)
    }

    fun getReservas(): List<Reserva> {
        return reservas
    }
}