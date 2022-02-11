package com.example.roblemejorado.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.roblemejorado.R
import com.google.firebase.auth.FirebaseAuth

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth= FirebaseAuth.getInstance();
        var user=auth.currentUser

        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed(Runnable {
            intent = if(user!=null){
                Intent(this, MainActivity::class.java)
            }else{
                Intent(this, LoginActivity::class.java)
            }
            startActivity(intent)
            finish()
        },3000)
    }
}