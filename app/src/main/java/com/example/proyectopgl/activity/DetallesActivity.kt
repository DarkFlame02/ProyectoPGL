package com.example.proyectopgl.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectopgl.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetallesActivity : AppCompatActivity() {

    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalles)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configuracion de la toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        // Obtengo los datos del MainActivity
        val nombre = intent.getStringExtra("BONO_NOMBRE")
        val desc = intent.getStringExtra("BONO_DESC")
        val imagen = intent.getIntExtra("BONO_IMAGEN", 0)

        // Referencias a la vista para mostrar los datos
        val bonoNombre: TextView = findViewById(R.id.detalleTitle)
        val bonoDesc: TextView = findViewById(R.id.detalleDesc)
        val bonoImagen: ImageView = findViewById(R.id.detalleImage)

        // Asigno los datos
        bonoNombre.text = nombre
        bonoDesc.text = desc
        bonoImagen.setImageResource(imagen)

        //Configuracion del FloatingActionButton
        fab = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, FormularioActivity::class.java)

            // Paso la imagen al formulario
            intent.putExtra("BONO_IMAGEN", imagen)
            startActivity(intent)
        }

    }
}












