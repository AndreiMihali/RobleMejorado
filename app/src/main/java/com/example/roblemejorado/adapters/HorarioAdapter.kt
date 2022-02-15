package com.example.roblemejorado.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roblemejorado.R
import com.example.roblemejorado.model.Asignatura
import com.google.android.material.card.MaterialCardView

class HorarioAdapter(val context: Context, val data:ArrayList<Asignatura>): RecyclerView.Adapter<HorarioAdapter.ViewHolder>() {

    private val itemSelected=0

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txt_asignatura:TextView
        var txt_horaInicio:TextView
        var txt_horaFin:TextView
        var card_asignatura:MaterialCardView

        init {
            txt_asignatura=itemView.findViewById(R.id.txt_asignatura)
            txt_horaFin=itemView.findViewById(R.id.horaFin)
            txt_horaInicio=itemView.findViewById(R.id.hora_inicio)
            card_asignatura=itemView.findViewById(R.id.card_Asignatura)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.item_asignatura,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txt_asignatura.text=data[position].nombre
        holder.txt_horaInicio.text=data[position].horaInicio
        holder.txt_horaFin.text=data[position].horaFin
        holder.card_asignatura.setCardBackgroundColor(Color.parseColor(data[position].color))
    }

    override fun getItemCount(): Int {
        return data.size
    }
}