package com.example.roblemejorado.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.example.roblemejorado.R

class SettingsActivity : AppCompatActivity() {
    private lateinit var toolbar:Toolbar
    private lateinit var ln_cuenta:LinearLayout
    private lateinit var ln_notf:LinearLayout
    private lateinit var ln_mens:LinearLayout
    private lateinit var ln_alm:LinearLayout
    private lateinit var ayuda:LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_FullScreenDialog)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        init()
    }

    private fun init(){
        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        ln_cuenta=findViewById(R.id.ln_cuenta)
        ln_notf=findViewById(R.id.ln_notificaciones)
        ln_mens=findViewById(R.id.ln_mensajes)
        ln_alm=findViewById(R.id.ln_storage)
        ayuda=findViewById(R.id.ln_ayuda)

        initListeners()

    }

    private fun initListeners(){
        toolbar.setNavigationOnClickListener {
            onBackPressed()
            finish()
        }
        ln_cuenta.setOnClickListener {
            showDialog()
        }
        ln_notf.setOnClickListener {
            showDialog()
        }
        ln_alm.setOnClickListener {
            showDialog()
        }
        ln_mens.setOnClickListener {
            showDialog()
        }
        ayuda.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog(){
        val builder=AlertDialog.Builder(this).apply {
            title="Atención"
            setMessage("Esta opción esta en desarrollo")
            setPositiveButton("Aceptar") { dialog, wich ->
                    dialog.cancel()
            }
        }
        val dialog=builder.create()
        dialog.show()
    }
}