package com.example.roblemejorado.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roblemejorado.R
import com.example.roblemejorado.adapters.NotaAdapter
import com.example.roblemejorado.model.Notas
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.roundToInt


class notasFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var grupo_botones:MaterialButtonToggleGroup
    private lateinit var txt_evaluacion:TextView
    private lateinit var data:ArrayList<Notas>
    private lateinit var adapter:NotaAdapter
    private lateinit var txt_media:TextView
    private lateinit var media:TextView
    private lateinit var card_media:MaterialCardView
    private lateinit var card_media_global:MaterialCardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_notas, container, false)
        recyclerView=view.findViewById(R.id.recycler)
        grupo_botones=view.findViewById(R.id.toggleButton)
        txt_evaluacion=view.findViewById(R.id.txt_evaluacion)
        txt_media=view.findViewById(R.id.media)
        media=view.findViewById(R.id.nota_media)
        card_media=view.findViewById(R.id.card_media)
        card_media_global=view.findViewById(R.id.card_media_global)
        data= ArrayList()
        recyclerView.layoutManager=LinearLayoutManager(activity?.applicationContext)

        getData()

        grupo_botones.addOnButtonCheckedListener{toggleButton, checkedId, isChecked->
            when(checkedId){
                R.id.button1->{txt_evaluacion.text="1º EVALUACIÓN"
                    recyclerView.removeAllViews()
                    getData()
                    card_media_global.visibility=View.VISIBLE}
                R.id.button1_1->{txt_evaluacion.text="RECUPERACION 1º EVALUACIÓN"
                    data.clear()
                    recyclerView.removeAllViews()
                    verMensaje()
                    card_media_global.visibility=View.INVISIBLE}
                R.id.button2->{txt_evaluacion.text="ORDINARIA"
                    data.clear()
                    recyclerView.removeAllViews()
                    verMensaje()
                    card_media_global.visibility=View.INVISIBLE}
                R.id.button3->{txt_evaluacion.text="EXTRAORDINARIA"
                    data.clear()
                    recyclerView.removeAllViews()
                    verMensaje()
                    card_media_global.visibility=View.INVISIBLE}
            }
        }
        return view;
    }

    private fun getData(){
        data.clear()
        var auth=FirebaseAuth.getInstance().currentUser
        var bd=FirebaseFirestore.getInstance()
        var asignaturas: java.util.HashMap<String,Int>
        var media=0.0
        auth?.let {
            bd.collection("users").document(auth.email!!).get().addOnSuccessListener {
                asignaturas=it.get("1_EVA") as HashMap<String, Int>
                asignaturas.forEach{(key,value) ->
                    data.add(Notas(key,value))
                    media+=value
                }
                data.let {
                    adapter= NotaAdapter(data,activity?.applicationContext!!)
                    recyclerView.adapter=adapter
                    pintarMedia("1º Evaluación",media)
                }
                if (data.isEmpty())Toast.makeText(activity?.applicationContext,"No hay notas en este momento",Toast.LENGTH_LONG).show()
            }.addOnFailureListener{
                Log.d("ERROR EN CARGA DE NOTAS","ERROR AL CARGAR LAS NOTAS "+it.message)
                Toast.makeText(activity?.applicationContext,"No hay notas en este momento",Toast.LENGTH_LONG);
            }
        }
    }

    private fun verMensaje(){
        Toast.makeText(activity?.applicationContext,"No hay notas en este momento",Toast.LENGTH_LONG).show()
    }

    private fun pintarMedia(evalu:String,media:Double){
        txt_media.text="Media $evalu"
        if(media/data.size>=5){
            card_media.setCardBackgroundColor(Color.parseColor("#259200"))
        }else{
            card_media.setCardBackgroundColor(Color.parseColor("#ba000d"))
        }
        this.media.text= (media / data.size).roundToInt().toString()
    }


}