package com.example.proyectopgl.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectopgl.R
import com.example.proyectopgl.adapters.ReservasAdapter
import com.example.proyectopgl.datos.ReservaRepository

class ReservasActivity : AppCompatActivity() {

    private lateinit var rcv: RecyclerView
    private lateinit var adapter: ReservasAdapter
    private var listaNombres = mutableListOf<String>()
    private var listaFechas = mutableListOf<String>()
    private var listaHoras = mutableListOf<String>()
    private var listaImagenes = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reservas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configuracion de la toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Recibo los datos de las reservas
        listaNombres = intent.getStringArrayListExtra("NOMBRES")?.toMutableList() ?: mutableListOf()
        listaFechas = intent.getStringArrayListExtra("FECHAS")?.toMutableList() ?: mutableListOf()
        listaHoras = intent.getStringArrayListExtra("HORAS")?.toMutableList() ?: mutableListOf()

        // Inicio la interfaz
        initUi()

    }

    // Inicializa los componentes de la interfaz y los datos
    private fun initUi() {
        initDatos()
        initRecyclerView()
        actualizarRecyclerView()
    }

    // Carga los datos y actualiza la lista
    private fun initDatos() {
        val reservas = ReservaRepository.getReservas()
        listaNombres.clear()
        listaFechas.clear()
        listaHoras.clear()
        listaImagenes.clear()

        reservas.forEach { reserva ->
            listaNombres.add(reserva.nombre)
            listaFechas.add(reserva.fecha)
            listaHoras.add(reserva.hora)
            listaImagenes.add(reserva.imagen)
        }
    }

    // Configuro el RecyclerView
    private fun initRecyclerView() {
        rcv = findViewById(R.id.listaRecyclerView)
        rcv.layoutManager = LinearLayoutManager(this)

        adapter = ReservasAdapter(
            listaNombres, listaFechas, listaHoras, listaImagenes,
            onItemClick = { position -> mostrarReserva(position) },
            onLongClick = { position -> mostrarMenuContextual(position) }
        )

        rcv.adapter = adapter

    }

    // Notificao al adapatador que los datos han sido modificados para que actualice la vista
    private fun actualizarRecyclerView() {
        adapter.notifyDataSetChanged()
    }

    // Muestra un mensaje con la informacion de la reserva
    private fun mostrarReserva(position: Int) {
        Toast.makeText(
            this,
            getString(R.string.reserva, listaNombres[position], listaFechas[position], listaHoras[position]),
            Toast.LENGTH_LONG
        ).show()
    }

    // Muestra el menu contextual en la reserva seleccionada para poder eliminarla
    private fun mostrarMenuContextual(position: Int) {
        val view = rcv.findViewHolderForAdapterPosition(position)?.itemView
        val menu = android.widget.PopupMenu(this, view)
        menu.menuInflater.inflate(R.menu.context_menu, menu.menu)

        menu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete -> {
                    eliminarReserva(position)
                    true
                }
                else -> false
            }
        }

        menu.show()
    }

    // Elimina la reserva y actualiza la lista
    private fun eliminarReserva(position: Int) {
        ReservaRepository.eliminarReserva(position)
        initDatos()
        adapter.notifyItemRemoved(position)
        Toast.makeText(this, "Cita eliminada", Toast.LENGTH_SHORT).show()
    }

    // Creo el menu de opciones
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    // Manejo de las opciones del menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mnOp1 -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.mnOp2 -> {
                Toast.makeText(this, getString(R.string.reservasmenu), Toast.LENGTH_SHORT).show()
                true
            }
            android.R.id.home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}