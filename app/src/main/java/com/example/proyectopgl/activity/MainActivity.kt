package com.example.proyectopgl.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectopgl.R
import com.example.proyectopgl.adapters.BonoAdapter
import com.google.android.material.navigation.NavigationView
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var rcv: RecyclerView
    private lateinit var adapter: BonoAdapter
    private lateinit var navigationView: NavigationView

    private var listaNombres = mutableListOf<String>()
    private var listaDesc = mutableListOf<String>()
    private var listaImagen = arrayOf(
        R.drawable.imagen1,
        R.drawable.imagen2,
        R.drawable.imagen3,
        R.drawable.imagen4,
        R.drawable.imagen5,
        R.drawable.imagen6,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        navigationView = findViewById(R.id.navigation_view)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_pref -> {
                    mostrarDialogoTema()
                }
                R.id.nav_info -> {
                    mostrarInformacionApp()
                }
                R.id.nav_exit -> {
                    mostrarConfirmacionSalida()
                }
            }
            true
        }

        initUi()
    }

    private fun mostrarConfirmacionSalida() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.confirmar_salida))
        builder.setMessage(getString(R.string.estas_seguro_que_deseas_salir))
        builder.setPositiveButton(getString(R.string.si)) { dialog, _ ->
            finishAffinity()
            exitProcess(0)
        }
        builder.setNegativeButton(getString(R.string.no)) { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    private fun mostrarInformacionApp() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.informacion))
        builder.setMessage(getString(R.string.descripcion_app))
        builder.setPositiveButton(getString(R.string.cerrar)) { dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
    }

    private fun mostrarDialogoTema() {
        val opciones = arrayOf("Predeterminado por el sistema", "Claro", "Oscuro")
        var temaSeleccionado = 0

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Selecciona un tema")
        builder.setSingleChoiceItems(opciones, temaSeleccionado) { _, which ->
            temaSeleccionado = which
        }
        builder.setPositiveButton("Aceptar") { _, _ ->
            when (temaSeleccionado) {
                0 -> setThemeMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                1 -> setThemeMode(AppCompatDelegate.MODE_NIGHT_NO)
                2 -> setThemeMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
        builder.setNegativeButton("Cancelar", null)

        val dialog = builder.create()
        dialog.show()
    }

    private fun setThemeMode(mode: Int) {
        AppCompatDelegate.setDefaultNightMode(mode)
        recreate()
    }

    private fun initUi() {
        initDatos()
        initRecyclerView()
    }

    private fun initDatos() {
        listaNombres = resources.getStringArray(R.array.nombreBonos).toMutableList()
        listaDesc = resources.getStringArray(R.array.descBonos).toMutableList()
    }

    private fun initRecyclerView() {
        rcv = findViewById(R.id.listaRecyclerView)
        rcv.layoutManager = LinearLayoutManager(this)

        adapter = BonoAdapter(listaNombres, listaDesc, listaImagen) { nombre, desc, imagen ->
            // Muestra los detalles del bono en una nueva actividad
            val intent = Intent(this, DetallesActivity::class.java).apply {
                putExtra("BONO_NOMBRE", nombre)
                putExtra("BONO_DESC", desc)
                putExtra("BONO_IMAGEN", imagen)
            }
            startActivity(intent)
        }

        rcv.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mnOp1 -> {
                Toast.makeText(this, getString(R.string.bonosmenu), Toast.LENGTH_SHORT).show()
                true
            }
            R.id.mnOp2 -> {
                val intent = Intent(this, ReservasActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}