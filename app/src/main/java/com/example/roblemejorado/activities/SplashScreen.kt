package com.example.roblemejorado.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.roblemejorado.R

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {

    private lateinit var sp:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sp=getSharedPreferences(getString(R.string.preferences), Context.MODE_PRIVATE)

        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed(Runnable {
            intent = if(sp.getBoolean("Logeado",false)){
                Intent(this, MainActivity::class.java)
            }else{
                Intent(this, LoginActivity::class.java)
            }
            startActivity(intent)
            finish()
        },3000)
    }
}