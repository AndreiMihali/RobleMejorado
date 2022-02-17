package com.example.roblemejorado.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roblemejorado.activities.ChatActivity
import com.example.roblemejorado.R
import com.example.roblemejorado.adapters.MensajesAdapter
import com.example.roblemejorado.model.UsuariosMensajes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class faltasFragment : Fragment(){

    private lateinit var recyclerView:RecyclerView
    private lateinit var data:ArrayList<UsuariosMensajes>
    private lateinit var adapter:MensajesAdapter
    private lateinit var firestore:FirebaseFirestore
    private lateinit var user:UsuariosMensajes

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_faltas, container, false)
        recyclerView=view.findViewById(R.id.recycler_mensages)
        recyclerView.layoutManager=LinearLayoutManager(activity?.applicationContext!!)
        data= ArrayList()
        firestore= FirebaseFirestore.getInstance()
        getData()
        return view;
    }

    private fun getData(){
        firestore.collection("users").get().addOnSuccessListener {
            for(query in it){
                val userId=query.get("userId").toString()
                val usuario=query.get("usuario").toString()
                val mensaje=query.get("mensaje").toString()
                val foto=query.get("iamgenPerfil").toString()

                user=UsuariosMensajes(usuario,foto,mensaje,userId)

                if (userId != null && userId != FirebaseAuth.getInstance().currentUser?.uid) {
                    data.add(user)
                }
            }
            adapter=MensajesAdapter(activity?.applicationContext!!,data)
            recyclerView.adapter=adapter
        }.addOnFailureListener{
            Toast.makeText(activity?.applicationContext,"Error",Toast.LENGTH_LONG).show()
        }
    }

}