package com.example.roblemejorado.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.roblemejorado.R
import com.example.roblemejorado.common.Companion
import com.jsibbold.zoomage.ZoomageView

class VerImagenActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var imagen:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ImagenPerfilVista)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_imagen)
        init()
    }

    private fun init(){
        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        imagen=findViewById(R.id.iamgenPerfil)
        imagen.setImageBitmap(Companion.BITMAP)
        initListeners()
    }

    private fun initListeners() {
        toolbar.setNavigationOnClickListener {
            onBackPressed()
            finish()
        }
    }
}