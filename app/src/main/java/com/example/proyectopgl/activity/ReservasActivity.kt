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

class ReservasActivity:AppCompatActivity() {

    lateinit var rcv: RecyclerView

    lateinit var adapter: ReservasAdapter

    var listaDias = mutableListOf<String>()
    var listaFechas = mutableListOf<String>()
    var listaHoras = mutableListOf<String>()

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

        initUi()
    }

    // Inicializamos todos nuestros componentes
    private fun initUi() {
        initDatos()
        initRecyclerView()
    }

    // Asignamos los textos a la estructura de datos
    private fun initDatos() {
        listaDias = resources.getStringArray(R.array.dias).toMutableList()
        listaFechas = resources.getStringArray(R.array.fechas).toMutableList()
        listaHoras = resources.getStringArray(R.array.horas).toMutableList()
    }

    // Inicaliza el RecyclerView
    private fun initRecyclerView() {
        rcv = findViewById(R.id.listaRecyclerView)
        rcv.layoutManager = LinearLayoutManager(this)

        // Pasamos los nombres, descripciones e imágenes al adaptador
        adapter = ReservasAdapter(listaDias, listaFechas, listaHoras) { position ->
            mostrarPos(position)
        }

        rcv.adapter = adapter
    }

    // Método que se lanza al hacer click en uno de los elementos del listado
    private fun mostrarPos(position: Int) {
        Toast.makeText(
            this,
            getString(R.string.toast_text, listaFechas[position]),
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
                Toast.makeText(this, getString(R.string.toast_text), Toast.LENGTH_SHORT).show()
                true
            }
            android.R.id.home -> {
                // Manejo del botón de retroceso
                onBackPressed()  // Esto cierra la actividad y vuelve a la anterior
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}