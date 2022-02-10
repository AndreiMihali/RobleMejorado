package com.example.roblemejorado.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.roblemejorado.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var layout_username:TextInputLayout
    private lateinit var edit_username:TextInputEditText
    private lateinit var layout_password:TextInputLayout
    private lateinit var edit_password:TextInputEditText
    private lateinit var acceder:MaterialButton
    private lateinit var sp:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    private fun init(){
        layout_username=findViewById(R.id.layout_username)
        edit_username=findViewById(R.id.edit_username)
        layout_password=findViewById(R.id.layout_password)
        edit_password=findViewById(R.id.edit_pasword)
        acceder=findViewById(R.id.acceder)
        acceder.isEnabled=false
        sp=getSharedPreferences(getString(R.string.preferences), Context.MODE_PRIVATE)
        putListeners()
    }

    private fun putListeners(){
        layout_username.addOnEditTextAttachedListener {
            if(it.childCount>=1){
                layout_username.endIconMode=TextInputLayout.END_ICON_CLEAR_TEXT
            }
        }

        edit_username.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                layout_username.isErrorEnabled=false
            }else{
                if(edit_username.text.toString().isEmpty()){
                    layout_username.error="Debe introducir un usuario"
                    acceder.isEnabled=false
                }else{
                    acceder.isEnabled=true
                }
            }
        }

        edit_password.setOnFocusChangeListener{v,hasFocus->
            if(hasFocus){
                layout_password.isErrorEnabled=false
            }else{
                if(edit_password.text.toString().isEmpty()){
                    layout_password.error="Debe introducir una contraseña"
                    acceder.isEnabled=false
                }else{
                    acceder.isEnabled=true
                }
            }
        }

        acceder.setOnClickListener {

            if(edit_username.text.isNullOrEmpty()||edit_password.text.isNullOrEmpty()){
                Snackbar.make(findViewById(R.id.lm),"Debe rellenar ambos campos",Snackbar.LENGTH_LONG).show()
            }else {
                if (edit_username.text.toString() == "andrei.mihali" && edit_password.text.toString() == "andrei") {
                    sp.edit().apply() {
                        putBoolean("Logeado", true)
                    }.commit()

                    val intent = Intent(this, MainActivity::class.java)
                    sp.edit().apply() {
                        putString("username", edit_username.text.toString())
                    }.commit()
                    startActivity(intent)
                    finish()
                } else {
                    Snackbar.make(
                        findViewById(R.id.lm),
                        "Usuario o contraseña incorrectos",
                        Snackbar.LENGTH_LONG
                    ).show()
                    edit_password?.text = null
                    edit_username?.text = null
                    layout_username.isErrorEnabled = false
                    layout_password.isErrorEnabled = false
                }
            }
        }
    }

}