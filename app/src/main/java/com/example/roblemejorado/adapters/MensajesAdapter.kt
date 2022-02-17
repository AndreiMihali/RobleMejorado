package com.example.roblemejorado.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.roblemejorado.R
import com.example.roblemejorado.activities.ChatActivity
import com.example.roblemejorado.model.UsuariosMensajes
import com.google.android.material.card.MaterialCardView

class MensajesAdapter(val context:Context,val data:ArrayList<UsuariosMensajes>): RecyclerView.Adapter<MensajesAdapter.ViewHolder>(){

    var itemSelected=0


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val foto_perfil:ImageView
        val txt_email_usuario:TextView
        val txt_mensaje:TextView
        init {
            foto_perfil=itemView.findViewById(R.id.imagen_perfil)
            txt_email_usuario=itemView.findViewById(R.id.email_usuario)
            txt_mensaje=itemView.findViewById(R.id.mensaje)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.item_mensage_user,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txt_mensaje.text=data[position]?.mensaje
        holder.txt_email_usuario.text=data[position].usuario
        Glide.with(context).load(data[position].imagenPerfil).into(holder.foto_perfil)
        val user=data[position]
        holder.itemView.setOnClickListener{
            context.startActivity(Intent(context, ChatActivity::class.java).apply {
                    setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra("userId",user.idUsuario)
                        .putExtra("userName",user.usuario)
                        .putExtra("userProfile",user.imagenPerfil)
                })
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}