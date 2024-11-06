package com.example.citamedica

data class Cita(

    val id: Int,
    val descripcion: String,
    val especialidad: String,
    val tipoConsulta: String,
    val medico: String,
    val fecha: String,
    val hora: String
)
