package com.example.roblemejorado.activities

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.roblemejorado.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var layout_username:TextInputLayout
    private lateinit var edit_username:TextInputEditText
    private lateinit var layout_password:TextInputLayout
    private lateinit var edit_password:TextInputEditText
    private lateinit var acceder:MaterialButton
    private lateinit var progressDialog:ProgressDialog
    private lateinit var firebase:FirebaseFirestore

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
        firebase= FirebaseFirestore.getInstance()
        progressDialog= ProgressDialog(this)
        putListeners()
    }

    private fun putListeners() {
        layout_username.addOnEditTextAttachedListener {
            if (it.childCount >= 1) {
                layout_username.endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
            }
        }

        edit_username.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                layout_username.isErrorEnabled = false
            } else {
                if (edit_username.text.toString().isEmpty()) {
                    layout_username.error = "Debe introducir un usuario"
                }
            }

            edit_password.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    layout_password.isErrorEnabled = false
                } else {
                    if (edit_password.text.toString().isEmpty()) {
                        layout_password.error = "Debe introducir una contraseña"
                    }
                }
            }

            acceder.setOnClickListener {
                progressDialog.setMessage("Espere por favor...")
                progressDialog.show()
                if (edit_username.text.isNullOrEmpty() || edit_password.text.isNullOrEmpty()) {
                    Snackbar.make(
                        findViewById(R.id.lm),
                        "Debe rellenar ambos campos",
                        Snackbar.LENGTH_LONG
                    ).show()
                    progressDialog.dismiss()
                } else {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(
                        edit_username.text!!.trim().toString(),
                        edit_password.text!!.trim().toString()
                    )
                        .addOnCompleteListener(OnCompleteListener {
                            if (it.isSuccessful) {
                                progressDialog.dismiss()
                                val intent = Intent(this, MainActivity::class.java)
                                getSharedPreferences(getString(R.string.preferences), Context.MODE_PRIVATE).edit().apply(){
                                    putString("contrasenaUsuario",edit_password.text!!.trim().toString())
                                }.commit()
                                startActivity(intent)
                                finish()
                            } else {
                                progressDialog.dismiss()
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
                        })
                }
            }
        }
    }
}