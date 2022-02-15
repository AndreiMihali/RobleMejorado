package com.example.roblemejorado.activities

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.roblemejorado.R
import com.example.roblemejorado.common.Companion
import com.example.roblemejorado.common.UsuarioCompartido
import com.example.roblemejorado.fullScreenDialog.FullScreenDialog
import com.example.roblemejorado.model.Usuario
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class ProfileActivity : AppCompatActivity() {

    private lateinit var toolbar:Toolbar
    private lateinit var card_foto:MaterialCardView
    private lateinit var txt_correo:TextView
    private lateinit var txt_matricula:TextView
    private lateinit var foto_pefil:ImageView
    private lateinit var ln_datos:LinearLayout
    private lateinit var ln_faltas:LinearLayout
    private lateinit var ln_agenda:LinearLayout
    private lateinit var bundle:Bundle
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var fab_camara:FloatingActionButton
    private val PERMISSION_CODE_CAMERA=1
    private val PERMISSION_CODE_GALLERY=2
    private lateinit var bottomSheetPick:BottomSheetDialog
    private lateinit var uri:Uri
    private lateinit var firestore:FirebaseFirestore
    private lateinit var progressDialog: ProgressDialog
    private var curso=""
    private var tutor=""
    private var nombre=""
    private var usuario=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        init()
        getUserData()
    }

    private fun init(){
        bundle=intent?.extras!!
        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        card_foto=findViewById(R.id.card_perfil)
        txt_correo=findViewById(R.id.text_correo)
        txt_matricula=findViewById(R.id.text_matriculado)
        foto_pefil=findViewById(R.id.imagenPerfil)
        ln_datos=findViewById(R.id.ln_datos)
        ln_faltas=findViewById(R.id.ln_faltas)
        ln_agenda=findViewById(R.id.ln_agenda)
        fab_camara=findViewById(R.id.fb_cambiar_foto_perfil)
        firestore= FirebaseFirestore.getInstance()
        progressDialog= ProgressDialog(this)
        setData()
        initListeners()
        getActivityResult()

    }

    private fun initListeners(){
        toolbar.setNavigationOnClickListener {
            onBackPressed()
            finish()
        }

        fab_camara.setOnClickListener{
            showBottomSheePick()
        }

        card_foto.setOnClickListener{
            foto_pefil.invalidate()
            val bitmapDrawable=foto_pefil.drawable as BitmapDrawable
            val bitmap = bitmapDrawable.bitmap
            Companion.BITMAP = bitmap
            val activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this@ProfileActivity,
                foto_pefil,
                "image"
            )
            val intent = Intent(this@ProfileActivity, VerImagenActivity::class.java)
            startActivity(intent, activityOptionsCompat.toBundle())
        }

        ln_datos.setOnClickListener{
            val dialogFragment = FullScreenDialog.newInstance(Usuario(curso, tutor, nombre, usuario, null))
            UsuarioCompartido.USUARIO.contra=getSharedPreferences(getString(R.string.preferences),Context.MODE_PRIVATE).getString("contrasenaUsuario","").toString()
            dialogFragment?.show(supportFragmentManager, "tag")
        }

    }

    private fun showBottomSheePick() {
        val view=layoutInflater.inflate(R.layout.bottom_shett_pick,null)
        val linear_camera=view.findViewById<LinearLayout>(R.id.camera)
        val linear_gallery=view.findViewById<LinearLayout>(R.id.gallery)

        linear_camera.setOnClickListener{
            abrirCamar()
            bottomSheetPick.dismiss()
        }


        linear_gallery.setOnClickListener{
            linear_gallery.setBackgroundColor(Color.parseColor("#ba000d"))
            abrirGaleria()
            bottomSheetPick.dismiss()
        }

        bottomSheetPick= BottomSheetDialog(this)
        bottomSheetPick.setContentView(view)
        bottomSheetPick.setOnDismissListener {
            bottomSheetPick.cancel()
        }
        bottomSheetPick.show()
    }

    private fun abrirGaleria() {
        val permissionCheck=ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)

        if(permissionCheck!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),PERMISSION_CODE_GALLERY)
        }else{
            llamarGaleria()
        }
    }

    private fun abrirCamar() {
        val permissionCheck=ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)

        if(permissionCheck!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),PERMISSION_CODE_CAMERA)
        }else{
            llamarCamara()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_CODE_CAMERA ->{
                if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    llamarCamara()
                }
                return
            }
            PERMISSION_CODE_GALLERY->{
                if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    llamarGaleria()
                }
            }
        }
    }

    private fun llamarGaleria() {
        val gallery=Intent(Intent.ACTION_GET_CONTENT)
        gallery.addCategory("android.intent.category.OPENABLE")
        gallery.type = "*/*"
        activityResultLauncher.launch(gallery)
    }

    private fun llamarCamara() {
        val photo=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        activityResultLauncher.launch(photo)
    }

    private fun setData(){
        toolbar.title=bundle.getString("nombre")
        toolbar.setTitleTextColor(Color.parseColor("#ba000d"))
        txt_correo.text=bundle.getString("correo")
        txt_matricula.text=bundle.getString("matriculado")
        Glide.with(applicationContext).load(bundle.getString("imagen")).into(foto_pefil)
    }

    private fun getActivityResult(){
        activityResultLauncher=registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {
                if(it.resultCode==RESULT_OK){
                    val intent=it.data
                    uri=intent?.data as Uri
                    actualizarImagen()
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                    foto_pefil.setImageBitmap(bitmap)
                }
            }
        )
    }



    private fun actualizarImagen() {
        if(uri!=null){
            progressDialog.setMessage("Por favore espere...")
            progressDialog.show()
            val refernece=FirebaseStorage.getInstance().reference.child("imagenesPerfil/"+System.currentTimeMillis()+"_"+getFileExtension(uri))
            refernece.putFile(uri).addOnSuccessListener {
                val uriTask: Task<Uri> = it.storage.downloadUrl
                while (!uriTask.isSuccessful);
                val downloadUri = uriTask.result
                val downloadUrl = downloadUri.toString()
                val hashMap = HashMap<String, Any>()
                hashMap["iamgenPerfil"] = downloadUrl

                firestore.collection("users").document(FirebaseAuth.getInstance().currentUser?.email.toString()).update(hashMap)
                    .addOnSuccessListener(OnSuccessListener<Void?> {
                        progressDialog.dismiss()
                        Toast.makeText(
                            applicationContext,
                            "Se ha actualizado la foto correctamente",
                            Toast.LENGTH_SHORT
                        ).show()
                    })
            }
        }
    }

    private fun getFileExtension(uri: Uri): Any? {
        val contentResolver=contentResolver
        val mimeType=MimeTypeMap.getSingleton()
        return mimeType.getExtensionFromMimeType(contentResolver.getType(uri))
    }

    private fun getUserData() {
        FirebaseAuth.getInstance().currentUser.let {
            firestore.collection("users").document(FirebaseAuth.getInstance().currentUser?.email.toString()).get().addOnSuccessListener {
                curso=it.get("matriculado").toString()
                tutor=it.get("tutor").toString()
                nombre="${it.get("nombre").toString()} ${it.get("Apellidos").toString()}"
                usuario=it.get("usuario").toString()
            }.addOnFailureListener {
                Log.d("FullScreenDialog","Error al cargar los datos de la base de datos ${it.message}")
            }
        }
    }
}