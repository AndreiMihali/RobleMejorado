package com.example.roblemejorado

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.card.MaterialCardView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout:DrawerLayout
    private lateinit var navigationView:NavigationView
    private lateinit var openDrawer:MaterialCardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init();
    }

    private fun init(){
        val coordinatorLayout=findViewById<AppBarLayout>(R.id.app_bar) as CoordinatorLayout
        val toolbar=coordinatorLayout.findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawerLayout=findViewById(R.id.drawer_layout)
        navigationView=findViewById(R.id.my_NavView)
        replaceFragment(HomeFragment())

        openDrawer=findViewById(R.id.card_profile)
        openDrawer.setOnClickListener {
            drawerLayout.open()
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
        if(item.itemId==R.id.LogOut){
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.close()
        item.isChecked=true
        when (item.itemId){
            R.id.homeFragment->{
                replaceFragment(HomeFragment())
            }
            else -> return true
        }
        return true
    }

}