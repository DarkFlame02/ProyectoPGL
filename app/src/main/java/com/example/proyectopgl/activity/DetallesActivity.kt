package com.example.proyectopgl.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectopgl.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class DetallesActivity : AppCompatActivity() {

    lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        val nombre = intent.getStringExtra("BONO_NOMBRE")
        val desc = intent.getStringExtra("BONO_DESC")
        val imagen = intent.getIntExtra("BONO_IMAGEN", 0)

        val bonoNombre: TextView = findViewById(R.id.detalleTitle)
        val bonoDesc: TextView = findViewById(R.id.detalleDesc)
        val bonoImagen: ImageView = findViewById(R.id.detalleImage)

        bonoNombre.text = nombre
        bonoDesc.text = desc
        bonoImagen.setImageResource(imagen)

        // Se instancia el FloatingActionButton y se le agrega el listener
        fab = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, FormularioActivity::class.java)
            // Pasamos la imagen al formulario
            intent.putExtra("BONO_IMAGEN", imagen)
            startActivity(intent)
        }

    }
}












