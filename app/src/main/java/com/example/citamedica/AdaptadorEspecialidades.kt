package com.example.citamedica

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.citamedica.model.Specialty

class AdaptadorEspecialidades(private val listaEspecialidades:List<Specialty>):
    RecyclerView.Adapter<AdaptadorEspecialidades.EspecialidadViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EspecialidadViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.item_especialidades,parent,false)
        return EspecialidadViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listaEspecialidades.size
    }

    override fun onBindViewHolder(holder: EspecialidadViewHolder, position: Int) {
        val especialidades= listaEspecialidades[position]
        holder.img.setImageResource(especialidades.imagen)
        holder.t_especialidad.text =especialidades.nombreEspecialidad


    }

    class EspecialidadViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val img: ImageView=itemView.findViewById(R.id.imgEspecialidades)
        val t_especialidad: TextView=itemView.findViewById(R.id.tv_especialidades)
    }
}