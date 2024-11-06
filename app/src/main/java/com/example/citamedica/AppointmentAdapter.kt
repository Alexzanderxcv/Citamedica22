package com.example.citamedica

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.citamedica.model.Appointment

class AppointmentAdapter (private val appointments: ArrayList<Appointment>)
    : RecyclerView.Adapter<AppointmentAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvAppointmentId = itemView.findViewById<TextView>(R.id.tv_id)

        val tvDoctorName = itemView.findViewById<TextView>(R.id.tv_medico)

        val tvScheduledDate = itemView.findViewById<TextView>(R.id.tv_fecha)

        val tvScheduledTime = itemView.findViewById<TextView>(R.id.tv_hora)

        val tvSpecialty = itemView.findViewById<TextView>(R.id.tv_especialidad)

        val tvDescription = itemView.findViewById<TextView>(R.id.tv_descripcion)

        val tvType = itemView.findViewById<TextView>(R.id.tv_tipo)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(
           LayoutInflater.from(parent.context).inflate(R.layout.item_appointment, parent, false)
       )
    }

    override fun getItemCount() = appointments.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appointment = appointments[position]

        holder.tvAppointmentId.text = "Cita medica N#${appointment.id}"
        holder.tvDoctorName.text = appointment.doctorName
        holder.tvScheduledDate.text = "Atencion el dia ${appointment.scheduledDate}"
        holder.tvScheduledTime.text = "A las ${appointment.scheduledTime}"
        holder.tvDescription.text = "Razon: ${appointment.description}"
        holder.tvSpecialty.text= "Especialidad : ${appointment.especialidad}"
        holder.tvType.text= "Tipo: ${appointment.consulta}"
    }
}