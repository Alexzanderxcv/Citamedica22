package com.example.citamedica

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.citamedica.model.Appointment

class AppointmentsActivity : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper // DeclarAMOS para poder utilizarlo
    private lateinit var rvAppointments: RecyclerView
    private lateinit var appointmentAdapter: AppointmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointments)

        dbHelper = DBHelper(this)
        rvAppointments = findViewById(R.id.rv_appointment)


        try {
            val citas = dbHelper.getAllCitas()
            val appointments = citas.map { cita ->
                Appointment(
                    id = cita.id,
                    doctorName = cita.medico,
                    scheduledDate = cita.fecha,
                    scheduledTime = cita.hora,
                    description = cita.descripcion,
                    especialidad = cita.especialidad,
                    consulta = cita.tipoConsulta
                )
            } as ArrayList<Appointment>

            //  RecyclerView
            appointmentAdapter = AppointmentAdapter(appointments)
            rvAppointments.layoutManager = LinearLayoutManager(this)
            rvAppointments.adapter = appointmentAdapter
        } catch (e: Exception) {
            Log.e("MisCitas", "Error al obtener citas: ${e.message}")
            Toast.makeText(this, "Error al cargar citas", Toast.LENGTH_SHORT).show()
        }





    }
}
