package com.example.citamedica.model

data class Appointment(

    val id:  Int,
    val doctorName: String,
    val scheduledDate: String,
    val scheduledTime : String,
    val description : String,
    val especialidad: String,
    val consulta: String
)
