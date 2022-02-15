package com.example.roblemejorado.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.roblemejorado.fragments.VistaHorarioFragment
import kotlin.properties.Delegates

class TabHorarioAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){



    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> VistaHorarioFragment.newInstance(1)
            1-> VistaHorarioFragment.newInstance(2)
            2-> VistaHorarioFragment.newInstance(3)
            3-> VistaHorarioFragment.newInstance(4)
            4-> VistaHorarioFragment.newInstance(5)
            else-> Fragment()
        }
    }

    fun getTabTitle(position: Int):CharSequence{
        return when(position){
            0->"L"
            1->"M"
            2->"X"
            3->"J"
            4->"V"
            else->"null"
        }
    }

}