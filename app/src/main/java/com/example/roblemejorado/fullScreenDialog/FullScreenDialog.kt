package com.example.roblemejorado.fullScreenDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import com.example.roblemejorado.R

class FullScreenDialog: DialogFragment() {

    companion object{
        fun newInstance(): FullScreenDialog? {
            return FullScreenDialog()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_FullScreenDialog)
    }

    override fun onStart() {
        super.onStart()
        dialog!!.window!!.setWindowAnimations(R.style.dialogFullAniamtion)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =inflater.inflate(R.layout.vista_cambiar_datos,container,false)
        val toolbar=view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener{
            dismiss()
        }


        return view
    }



}