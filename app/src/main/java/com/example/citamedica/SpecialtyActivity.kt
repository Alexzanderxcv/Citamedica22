package com.example.citamedica

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.citamedica.model.Specialty

class SpecialtyActivity : AppCompatActivity() {

    private lateinit var recycler_especialidades: RecyclerView
    private lateinit var adaptador_especialidades:AdaptadorEspecialidades

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_specialty)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Llenar_data()
    }

    fun Llenar_data(){
        val listaEspecialidades = mutableListOf<Specialty>(

            Specialty(R.drawable.dermatologia, nombreEspecialidad = "Dermatologia"),
            Specialty(R.drawable.cardiologia, nombreEspecialidad = "Cardiologia"),
            Specialty(R.drawable.gastroenteritis, nombreEspecialidad = "Gastroenterologia"),
            Specialty(R.drawable.pediatria, nombreEspecialidad = "Pediatria"),
            Specialty(R.drawable.genecologia, nombreEspecialidad = "Ginecologia"),
            Specialty(R.drawable.endocrinologia, nombreEspecialidad = "Endocrinologia")




        )

        recycler_especialidades = findViewById(R.id.recyclerviewespecialidades)
        adaptador_especialidades = AdaptadorEspecialidades(listaEspecialidades)
        recycler_especialidades.adapter=adaptador_especialidades
        recycler_especialidades.layoutManager=LinearLayoutManager(this)



    }
}