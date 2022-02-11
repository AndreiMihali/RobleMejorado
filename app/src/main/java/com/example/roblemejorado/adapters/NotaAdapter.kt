package com.example.roblemejorado.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roblemejorado.R
import com.example.roblemejorado.model.Notas
import com.google.android.material.card.MaterialCardView

class NotaAdapter(val data:ArrayList<Notas>,val context:Context): RecyclerView.Adapter<NotaAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txt_asignatura:TextView
        var txt_nota:TextView
        var card_nota:MaterialCardView
        init {
            txt_asignatura=itemView.findViewById(R.id.nombre_asignatura)
            txt_nota=itemView.findViewById(R.id.nota_asignatura)
            card_nota=itemView.findViewById(R.id.card_nota)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaAdapter.ViewHolder {
        val inflater=LayoutInflater.from(context)
        val view=inflater.inflate(R.layout.item_nota,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotaAdapter.ViewHolder, position: Int) {
        holder.txt_asignatura.text=data[position].asignatura
        holder.txt_nota.text=data[position].nota.toString()
        if(data[position].nota<5){
            holder.card_nota.setCardBackgroundColor(Color.parseColor("#ba000d"))
        }else{
            holder.card_nota.setCardBackgroundColor(Color.parseColor("#259200"))
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}