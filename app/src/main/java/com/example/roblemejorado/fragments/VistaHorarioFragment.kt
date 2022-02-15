package com.example.roblemejorado.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roblemejorado.R
import com.example.roblemejorado.adapters.HorarioAdapter
import com.example.roblemejorado.model.Asignatura
import kotlin.math.asin

private const val ARG_PARAM1 = "param1"
class VistaHorarioFragment : Fragment() {

    private var param1: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
    }

    private lateinit var recycler:RecyclerView
    private lateinit var data:ArrayList<Asignatura>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_vista_horario, container, false)
        recycler=view.findViewById(R.id.recycler)
        data= ArrayList()
        when(param1){
            1->setDataL()
            2->setDataM()
            3->setDataX()
            4->setDataL()
            5->setDataX()
        }
        return view
    }

    private fun setDataL(){
        data.add(Asignatura("ITGS_DAM","16:00","16:55","#1E7D97"))
        data.add(Asignatura("AD","16:55","17:50","#AB2220"))
        data.add(Asignatura("AD","17:50","18:45","#AB2220"))
        data.add(Asignatura("Recreo","18:45","19:10","#5FAB20"))
        data.add(Asignatura("AD","19:10","20:05","#AB2220"))
        data.add(Asignatura("DI","20:05","21:00","#A920AB"))
        data.add(Asignatura("DI","21:00","21:55","#A920AB"))
        recycler.adapter=HorarioAdapter(activity?.applicationContext!!,data)
        recycler.layoutManager=LinearLayoutManager(activity?.applicationContext)

    }

    private fun setDataM(){
        data.add(Asignatura("PSP","16:00","16:55","#A7AB20"))
        data.add(Asignatura("PSP","16:55","17:50","#A7AB20"))
        data.add(Asignatura("EIE_DAM2","17:50","18:45","#20ABAB"))
        data.add(Asignatura("Recreo","18:45","19:10","#5FAB20"))
        data.add(Asignatura("AD","19:10","20:05","#AB2220"))
        data.add(Asignatura("AD","20:05","21:00","#AB2220"))
        data.add(Asignatura("AD","21:00","21:55","#AB2220"))
        recycler.adapter=HorarioAdapter(activity?.applicationContext!!,data)
        recycler.layoutManager=LinearLayoutManager(activity?.applicationContext)

    }

    private fun setDataX(){
        data.add(Asignatura("DI","16:00","16:55","#A920AB"))
        data.add(Asignatura("DI","16:55","17:50","#A920AB"))
        data.add(Asignatura("EIE_DAM2","17:50","18:45","#20ABAB"))
        data.add(Asignatura("Recreo","18:45","19:10","#5FAB20"))
        data.add(Asignatura("ITGS_DAM","19:10","20:05","#1E7D97"))
        data.add(Asignatura("SGE","20:05","21:00","#AB6E20"))
        data.add(Asignatura("SGE","21:00","21:55","#AB6E20"))
        recycler.adapter=HorarioAdapter(activity?.applicationContext!!,data)
        recycler.layoutManager=LinearLayoutManager(activity?.applicationContext)

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            VistaHorarioFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }

}