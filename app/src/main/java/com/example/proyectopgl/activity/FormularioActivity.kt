package com.example.proyectopgl.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectopgl.R
import com.example.proyectopgl.datos.Reserva
import com.example.proyectopgl.datos.ReservaRepository
import java.text.SimpleDateFormat
import java.util.*

class FormularioActivity : AppCompatActivity() {

    private var imagenBono: Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_formulario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recupero la imagen del bono desde la actividad anterior
        imagenBono = intent.getIntExtra("BONO_IMAGEN", 0)

        // Configuracion de la toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        val editTextNombre: EditText = findViewById(R.id.editTextNombre)
        val editTextApellidos: EditText = findViewById(R.id.editTextApellidos)
        val editTextCorreo: EditText = findViewById(R.id.editTextCorreo)
        val buttonFecha: Button = findViewById(R.id.buttonFecha)
        val buttonHora: Button = findViewById(R.id.buttonHora)
        val buttonConfirmar: Button = findViewById(R.id.buttonConfirmar)

        // Validacion de fecha y hora
         fun esFechaHoraValida(fecha: String, hora: String): Boolean {
            val calendar = Calendar.getInstance()
            val fechaHoraStr = "$fecha $hora"
            val formato = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

            return try {
                val fechaHora = formato.parse(fechaHoraStr)
                fechaHora != null && fechaHora.after(calendar.time)
            } catch (e: Exception) {
                false
            }
        }

        // Configuro el boton de fecha
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

        // Configuro el boton de hora
        buttonHora.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                val hora = String.format("%02d:%02d", selectedHour, selectedMinute)
                buttonHora.text = hora
            }, hour, minute, true).show()
        }

        // Configuro el boton de confirmar con todas las comprobaciones
        buttonConfirmar.setOnClickListener {
            val nombre = editTextNombre.text.toString().trim()
            val apellidos = editTextApellidos.text.toString().trim()
            val correo = editTextCorreo.text.toString().trim()
            val fecha = buttonFecha.text.toString()
            val hora = buttonHora.text.toString()

            // Validaciones
            if (nombre.isEmpty() || apellidos.isEmpty() || correo.isEmpty() ||
                fecha == getString(R.string.fecha) || hora == getString(R.string.hora)) {
                Toast.makeText(this, R.string.campos_incompletos, Toast.LENGTH_SHORT).show()
            }
            else if (nombre.isEmpty() || apellidos.isEmpty() || nombre.length < 2 || apellidos.length < 2) {
                Toast.makeText(this, R.string.nombre_apellidos_invalidos, Toast.LENGTH_SHORT).show()
            }
            else if (!nombre.matches("[a-zA-Z\\s]+".toRegex()) || !apellidos.matches("[a-zA-Z\\s]+".toRegex())) {
                Toast.makeText(this, R.string.nombre_apellidos_solo_letras, Toast.LENGTH_SHORT).show()
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                Toast.makeText(this, R.string.email_invalido, Toast.LENGTH_SHORT).show()
            }
            else if (!esFechaHoraValida(fecha, hora)) {
                Toast.makeText(this, R.string.fecha_hora_pasadas, Toast.LENGTH_SHORT).show()
            } else {
                ReservaRepository.addReserva(Reserva(nombre, fecha, hora, imagenBono))

                // Voy a la actividad de reservas
                val intent = Intent(this, ReservasActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
