package com.example.citamedica


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.citamedica.model.Doctor

class AdaptadorDoctor(private val listaDoctor:List<Doctor>):
    RecyclerView.Adapter<AdaptadorDoctor.DoctorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.item_doctor,parent,false)
        return DoctorViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listaDoctor.size
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctores= listaDoctor[position]
        holder.img.setImageResource(doctores.imagen)
        holder.t_doctor.text = doctores.nombreDoctor
        holder.t_apellido.text = doctores.apellidoDoctor
        holder.t_telefono.text = doctores.telefonoDoctor


    }

    class DoctorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val img: ImageView=itemView.findViewById(R.id.imgDoctor)
        val t_doctor: TextView=itemView.findViewById(R.id.tv_doctores)
        val t_apellido: TextView=itemView.findViewById(R.id.tv_apellido_doctor)
        val t_telefono: TextView=itemView.findViewById(R.id.tv_telefono)

    }
}