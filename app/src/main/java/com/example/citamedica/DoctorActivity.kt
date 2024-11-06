package com.example.citamedica

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.citamedica.model.Doctor

class DoctorActivity : AppCompatActivity() {

    private lateinit var recycler_doctor: RecyclerView
    private lateinit var adaptador_doctor:AdaptadorDoctor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_doctor)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Llenar_data()
    }

    fun Llenar_data(){
        val listaDoctor = mutableListOf<Doctor>(

            Doctor(R.drawable.doctoramendoza,  nombreDoctor = "Claudia", apellidoDoctor = "Mendoza Ramirez", telefonoDoctor = "987645678"),
            Doctor(R.drawable.doctorgarcia,  nombreDoctor = "Chris", apellidoDoctor = "Garcia Huaman", telefonoDoctor = "956213458"),
            Doctor(R.drawable.doctorgonzales,  nombreDoctor = "Diego", apellidoDoctor = "Gonzales Ramirez", telefonoDoctor = "995621457"),
            Doctor(R.drawable.doctorhernandez,  nombreDoctor = "Alejandro", apellidoDoctor = "Hernandez Mamani", telefonoDoctor = "974512365"),
            Doctor(R.drawable.doctorrodriguez,  nombreDoctor = "Piero", apellidoDoctor = "Rodriguez Abregu", telefonoDoctor = "954874125"),
            Doctor(R.drawable.medicodarwin    ,nombreDoctor = "Darwin", apellidoDoctor = "Rojas Guevara", telefonoDoctor = "95423425")



        )

        recycler_doctor = findViewById(R.id.recyclerviewdoctor)
        adaptador_doctor = AdaptadorDoctor(listaDoctor)
        recycler_doctor.adapter=adaptador_doctor
        recycler_doctor.layoutManager=LinearLayoutManager(this)



    }
}