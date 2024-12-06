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

    lateinit var rcv: RecyclerView
    lateinit var adapter: ReservasAdapter
    var listaNombres = mutableListOf<String>()
    var listaFechas = mutableListOf<String>()
    var listaHoras = mutableListOf<String>()
    var listaImagenes = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reservas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        listaNombres = intent.getStringArrayListExtra("NOMBRES")?.toMutableList() ?: mutableListOf()
        listaFechas = intent.getStringArrayListExtra("FECHAS")?.toMutableList() ?: mutableListOf()
        listaHoras = intent.getStringArrayListExtra("HORAS")?.toMutableList() ?: mutableListOf()

        initUi()

    }

    private fun initUi() {
        initDatos()
        initRecyclerView()
        actualizarRecyclerView()
    }

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

    private fun initRecyclerView() {
        rcv = findViewById(R.id.listaRecyclerView)
        rcv.layoutManager = LinearLayoutManager(this)

        adapter = ReservasAdapter(listaNombres, listaFechas, listaHoras, listaImagenes) { position ->
            mostrarPos(position)
        }

        rcv.adapter = adapter
    }

    private fun actualizarRecyclerView() {
        adapter.notifyDataSetChanged()
    }

    // Método que se lanza al hacer click en uno de los elementos del listado
    private fun mostrarPos(position: Int) {
        Toast.makeText(
            this,
            getString(R.string.reserva, listaNombres[position], listaFechas[position], listaHoras[position]),
            Toast.LENGTH_LONG
        ).show()
    }

    // Inflar el menú de opciones
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    // Manejar la selección de elementos del menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mnOp1 -> {
                // Acciones para "Opción 1"
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.mnOp2 -> {
                // Acciones para "Opción 1"
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