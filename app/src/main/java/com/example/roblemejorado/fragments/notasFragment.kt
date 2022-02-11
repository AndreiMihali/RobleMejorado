package com.example.roblemejorado.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roblemejorado.R
import com.example.roblemejorado.adapters.NotaAdapter
import com.example.roblemejorado.model.Notas
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class notasFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var grupo_botones:MaterialButtonToggleGroup
    private lateinit var txt_evaluacion:TextView
    private lateinit var data:ArrayList<Notas>
    private lateinit var adapter:NotaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_notas, container, false)
        recyclerView=view.findViewById(R.id.recycler)
        grupo_botones=view.findViewById(R.id.toggleButton)
        txt_evaluacion=view.findViewById(R.id.txt_evaluacion)
        data= ArrayList()
        getData()
        adapter= NotaAdapter(data,activity?.applicationContext!!)
        recyclerView.layoutManager=LinearLayoutManager(activity?.applicationContext)
        recyclerView.adapter=adapter

        grupo_botones.addOnButtonCheckedListener{toggleButton, checkedId, isChecked->
            when(checkedId){
                R.id.button1->txt_evaluacion.text="1º EVALUACIÓN"
                R.id.button1_1->txt_evaluacion.text="RECUPERACION 1º EVALUACIÓN"
                R.id.button2->txt_evaluacion.text="ORDINARIA"
                R.id.button3->txt_evaluacion.text="EXTRAORDINARIA"
            }
        }
        return view;
    }

    private fun getData(){
        var auth=FirebaseAuth.getInstance().currentUser
        var bd=FirebaseFirestore.getInstance()
        var asignaturas=ArrayList<String>()

        data.add(Notas("ASASASASasaS",8))
        data.add(Notas("ASASASASasaS",4))
        data.add(Notas("ASASASASasaS",8))
        data.add(Notas("ASASASASasaS",2))
        data.add(Notas("ASASASASasaS",8))
        data.add(Notas("ASASASASasaS",4))
        data.add(Notas("ASASASASasaS",3))
        data.add(Notas("ASASASASasaS",3))
        data.add(Notas("ASASASASasaS",10))
        data.add(Notas("ASASASASasaS",8))
    }

}