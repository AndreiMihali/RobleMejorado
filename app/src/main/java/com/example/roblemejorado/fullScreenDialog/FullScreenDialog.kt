package com.example.roblemejorado.fullScreenDialog

import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import com.example.roblemejorado.R
import com.example.roblemejorado.activities.ProfileActivity
import com.example.roblemejorado.common.UsuarioCompartido
import com.example.roblemejorado.model.Usuario
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class FullScreenDialog: DialogFragment() {

    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var progressDialog:ProgressDialog
    private lateinit var sp:SharedPreferences

    companion object{
        fun newInstance(usuario:Usuario): FullScreenDialog? {
            UsuarioCompartido.USUARIO=usuario
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


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =inflater.inflate(R.layout.vista_cambiar_datos,container,false)
        val toolbar=view.findViewById<Toolbar>(R.id.toolbar)
        sp= activity?.getSharedPreferences(getString(R.string.preferences),Context.MODE_PRIVATE)!!
        progressDialog= ProgressDialog(view.context)
        progressDialog.setMessage("Actualizando contraseña, por favor espere...")
        toolbar.setNavigationOnClickListener{
            dismiss()
        }

        view.findViewById<TextInputEditText>(R.id.ed_uid).setText(FirebaseAuth.getInstance().currentUser?.uid.toString())
        view.findViewById<TextInputEditText>(R.id.ed_correo).setText(FirebaseAuth.getInstance().currentUser?.email.toString())
        view.findViewById<TextInputEditText>(R.id.ed_matriculado).setText(UsuarioCompartido.USUARIO.curso)
        view.findViewById<TextInputEditText>(R.id.ed_nombre).setText(UsuarioCompartido.USUARIO.nombre)
        view.findViewById<TextInputEditText>(R.id.ed_tutor).setText(UsuarioCompartido.USUARIO.tutor)
        view.findViewById<TextInputEditText>(R.id.ed_usuario).setText(UsuarioCompartido.USUARIO.usuario)

        val btnCambiarContra=view.findViewById<MaterialButton>(R.id.btn_cambiarContrasena)

        btnCambiarContra.setOnClickListener {
            showBottomSheet()
        }

        return view
    }

    private fun showBottomSheet() {
        val view=layoutInflater.inflate(R.layout.bottom_sheet_cambiar_contra,null)
        val nuevaContra=view.findViewById<TextInputEditText>(R.id.nueavaContrasena)
        val aceptar=view.findViewById<MaterialButton>(R.id.btn_aceptar)
        val cancelar=view.findViewById<MaterialButton>(R.id.btn_cancelar)
        val layout=view.findViewById<TextInputLayout>(R.id.layout_contra)

        nuevaContra.setOnFocusChangeListener{f,focus->
            if(focus){
                layout.isErrorEnabled=false
            }
        }

        aceptar.setOnClickListener {
            when {
                nuevaContra.text?.isEmpty()!! -> {
                    layout.error="Debe introducir una contraseña"
                }
                nuevaContra.text?.length!!<6 -> {
                    layout.error="Debes introducir un mínimo de 6 carácteres"
                }
                else -> {
                    progressDialog.show()
                    val credential=EmailAuthProvider.getCredential(FirebaseAuth.getInstance().currentUser?.email.toString(),UsuarioCompartido.USUARIO.contra!!)
                    FirebaseAuth.getInstance().currentUser?.reauthenticate(credential)?.addOnSuccessListener {
                        FirebaseAuth.getInstance().currentUser?.updatePassword(nuevaContra.text.toString().trim())?.addOnSuccessListener {
                            Toast.makeText(view.context,"La contraseña se ha actualizado correctamente",Toast.LENGTH_LONG).show()
                            UsuarioCompartido.USUARIO.contra=nuevaContra.text.toString().trim()
                            sp.edit().apply(){
                                putString("contrasenaUsuario",nuevaContra.text.toString().trim())
                            }.commit()
                            bottomSheetDialog.dismiss()
                            progressDialog.dismiss()
                        }?.addOnFailureListener {
                            Log.d("FullScrennDialog","Error al actualizar la contraseña ${it.message}")
                            progressDialog.dismiss()
                            bottomSheetDialog.dismiss()
                        }
                    }?.addOnFailureListener{
                        Log.d("FullScrennDialog","Error al reauthenticarse ${it.message}")
                        progressDialog.dismiss()
                    }
                }
            }
        }

        bottomSheetDialog= BottomSheetDialog(view.context)
        bottomSheetDialog.setContentView(view)

        cancelar.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setOnDismissListener {
            bottomSheetDialog.cancel()
        }

        bottomSheetDialog.show()

    }


}