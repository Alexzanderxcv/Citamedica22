package com.example.citamedica

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.citamedica.model.Doctor
import com.example.citamedica.util.PreferenceHelper
import com.example.citamedica.util.PreferenceHelper.set

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnLogout = findViewById<Button>(R.id.btn_logout)
        btnLogout.setOnClickListener {
            clearSessionPreferences()
            goToLogin()
        }

        val btnReservarCita = findViewById<Button>(R.id.btn_reservar_cita)
        btnReservarCita.setOnClickListener{
            goToCreateAppointment()

        }

        val btnMisCitas = findViewById<Button>(R.id.btn_mis_citas)
        btnMisCitas.setOnClickListener {
            goToMyAppointment()

        }

        val btnListaEspecialidades = findViewById<Button>(R.id.btn_lista_especialidades)
        btnListaEspecialidades.setOnClickListener {
            goToMySpecialty()
        }

        val btnListaDoctores = findViewById<Button>(R.id.btn_lista_doctores)
        btnListaDoctores.setOnClickListener {
            goToMyDoctor()
        }

    }

    private fun goToMyDoctor(){
        val intent = Intent(this, DoctorActivity::class.java)
        startActivity(intent)
    }

    private fun goToMySpecialty(){
        val intent = Intent(this, SpecialtyActivity::class.java)
        startActivity(intent)
    }

    private fun goToMyAppointment(){
        val intent = Intent(this, AppointmentsActivity::class.java)
        startActivity(intent)
    }

    private fun goToCreateAppointment(){
        val i = Intent(this, CreateAppointmentActivity::class.java)
        startActivity(i)
    }

    private fun goToLogin(){
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }

    private fun clearSessionPreferences(){
        val preferences = PreferenceHelper.defaultPrefs(this)
        preferences["session"] = false
    }
}