package com.example.proyectopgl.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectopgl.R
import com.example.proyectopgl.datos.Reserva
import com.example.proyectopgl.datos.ReservaRepository
import java.util.*

class FormularioActivity : AppCompatActivity() {

    var imagenBono: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imagenBono = intent.getIntExtra("BONO_IMAGEN", 0)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        // Obtener referencias a los elementos de la interfaz
        val editTextNombre: EditText = findViewById(R.id.editTextNombre)
        val editTextApellidos: EditText = findViewById(R.id.editTextApellidos)
        val editTextCorreo: EditText = findViewById(R.id.editTextCorreo)
        val buttonFecha: Button = findViewById(R.id.buttonFecha)
        val buttonHora: Button = findViewById(R.id.buttonHora)
        val buttonConfirmar: Button = findViewById(R.id.buttonConfirmar)

        // Configurar botón para seleccionar fecha
        buttonFecha.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val fecha = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                buttonFecha.text = fecha
            }, year, month, day).show()
        }

        // Configurar botón para seleccionar hora
        buttonHora.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                val hora = String.format("%02d:%02d", selectedHour, selectedMinute)
                buttonHora.text = hora
            }, hour, minute, true).show()
        }

        buttonConfirmar.setOnClickListener {
            val nombre = editTextNombre.text.toString().trim()
            val apellidos = editTextApellidos.text.toString().trim()
            val correo = editTextCorreo.text.toString().trim()
            val fecha = buttonFecha.text.toString()
            val hora = buttonHora.text.toString()

            if (nombre.isEmpty() || apellidos.isEmpty() || correo.isEmpty() ||
                fecha == getString(R.string.fecha) || hora == getString(R.string.hora)) {
                Toast.makeText(this, R.string.campos_incompletos, Toast.LENGTH_SHORT).show()
            } else {
                // Guarda en el repositorio, incluyendo la imagen
                ReservaRepository.addReserva(Reserva(nombre, fecha, hora, imagenBono))

                // Cambia de actividad
                val intent = Intent(this, ReservasActivity::class.java)
                startActivity(intent)
            }
        }

    }
}
