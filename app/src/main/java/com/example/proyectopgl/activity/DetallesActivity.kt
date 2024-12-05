package com.example.proyectopgl.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectopgl.R

class DetallesActivity : AppCompatActivity() {

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
    }
}












