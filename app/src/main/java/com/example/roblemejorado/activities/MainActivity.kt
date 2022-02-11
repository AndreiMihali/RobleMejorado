package com.example.roblemejorado.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.roblemejorado.R
import com.example.roblemejorado.fragments.HomeFragment
import com.example.roblemejorado.fragments.faltasFragment
import com.example.roblemejorado.fragments.horarioFragment
import com.example.roblemejorado.fragments.notasFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.card.MaterialCardView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout:DrawerLayout
    private lateinit var navigationView:NavigationView
    private lateinit var openDrawer:MaterialCardView
    private lateinit var bd:FirebaseFirestore
    private lateinit var nombre:String
    private lateinit var apellidos: String
    private lateinit var link:String

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init(){
        val coordinatorLayout=findViewById<AppBarLayout>(R.id.app_bar) as CoordinatorLayout
        val toolbar=coordinatorLayout.findViewById<Toolbar>(R.id.toolbar)
        bd= FirebaseFirestore.getInstance()
        nombre=""
        apellidos=""
        link=""
        setSupportActionBar(toolbar)
        drawerLayout=findViewById(R.id.drawer_layout)
        navigationView=findViewById(R.id.my_NavView)
        replaceFragment(HomeFragment())
        getUserData()

        openDrawer=findViewById(R.id.card_profile)
        openDrawer.setOnClickListener {
            drawerLayout.open()
            val username=findViewById<TextView>(R.id.txt_menu_nombre)
            val em=findViewById<TextView>(R.id.txt_menu_email)

            username.text="$nombre $apellidos"
            em.text=FirebaseAuth.getInstance().currentUser?.email
        }
        navigationView.setNavigationItemSelectedListener(this)

    }


    private fun replaceFragment(fragment: Fragment) {
        val fragManager = supportFragmentManager
        val fragmnetTransaction = fragManager.beginTransaction()
        fragmnetTransaction.replace(R.id.frame_layout, fragment)
        fragmnetTransaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId== R.id.LogOut){
            val builder=AlertDialog.Builder(this)
            builder.apply {
                setMessage("¿Esta seguro de que desea salir?")
                setTitle("Confirmar")
            }.setPositiveButton("Aceptar") { dialog, which ->
                val auth=FirebaseAuth.getInstance()
                auth.signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }.setNegativeButton("Cancelar"){dialog,which ->
                dialog.dismiss()
            }
            val dialog=builder.create()
            dialog.show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.close()
        if(item.itemId!=R.id.profileActivity){
            item.isChecked=true
        }

        when (item.itemId){
            R.id.homeFragment -> replaceFragment(HomeFragment())
            R.id.faltasFragment ->replaceFragment(faltasFragment())
            R.id.notasFragment ->replaceFragment(notasFragment())
            R.id.horarioFragment ->replaceFragment(horarioFragment())
            else -> return true
        }
        return true
    }

    private fun getUserData(){
        val auth=FirebaseAuth.getInstance().currentUser

        auth?.email.let {
            bd.collection("users").document(it!!).get()
                .addOnSuccessListener {it2->
                        nombre=it2.getString("nombre").toString()
                        apellidos=it2.getString("Apellidos").toString()
                        link=it2.getString("centro_estudios").toString()
                   }
                }
        }

}