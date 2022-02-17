package com.example.roblemejorado.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.roblemejorado.R
import com.example.roblemejorado.adapters.AdapterChat
import com.example.roblemejorado.model.Chat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class ChatActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var imagenPerfil:ImageView
    private lateinit var nombreUsuario:TextView
    private lateinit var fab_send:FloatingActionButton
    private lateinit var bundle:Intent
    private lateinit var receiverId:String
    private lateinit var ed_text:EditText
    private  var adapter: AdapterChat? =null
    private lateinit var data:ArrayList<Chat>
    private lateinit var reference: DatabaseReference
    private lateinit var user:FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_FullScreenDialog)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        init()
    }

    private fun init(){
        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        recyclerView=findViewById(R.id.recycler_mensajes)
        imagenPerfil=findViewById(R.id.fotoPerfil)
        nombreUsuario=findViewById(R.id.nombreUsuario)
        fab_send=findViewById(R.id.btn_send)
        bundle=intent
        receiverId= bundle.getStringExtra("userId")!!
        reference=FirebaseDatabase.getInstance().reference
        ed_text=findViewById(R.id.ed_message)
        user=FirebaseAuth.getInstance()?.currentUser!!
        if(receiverId!=null){
            nombreUsuario.text=bundle.getStringExtra("userName")
            Glide.with(this).load(bundle.getStringExtra("userProfile")).into(imagenPerfil)
        }

        initClickListeners()
        data = java.util.ArrayList()
        val lm = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        lm.stackFromEnd = true
        recyclerView.layoutManager = lm
        readChatList()


    }

    private fun initClickListeners(){
        toolbar.setNavigationOnClickListener {
            onBackPressed()
            finish()
        }

        ed_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (TextUtils.isEmpty(ed_text.text.toString())) {
                    fab_send.setImageDrawable(getDrawable(R.drawable.ic_baseline_mic_24))
                } else {
                    fab_send.setImageDrawable(getDrawable(R.drawable.ic_baseline_send_24))
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        fab_send.setOnClickListener {
            if(!TextUtils.isEmpty(ed_text.text.toString())){
                sendTextMessage(ed_text.text.toString())
                ed_text.setText("")
            }
        }
    }

    private fun sendTextMessage(toString: String) {

        val currentDateTime=Calendar.getInstance()
        @SuppressLint("SimpleDateFormat")
        val currentTime=currentDateTime.time.toLocaleString()

        val t=Calendar.getInstance().time
        val formatt=SimpleDateFormat("HH:mm")
        val time=formatt.format(t)

        val chat=Chat(
            "$currentTime",
            toString,
            user.uid,
            receiverId,
            time.toString()
        )

        val map=HashMap<String,String>()
        map["mensaje"] = toString

        reference.child("Chats").push().setValue(chat).addOnSuccessListener {
            Log.d(
                "Send",
                "onSuccess"
            )
        }.addOnFailureListener { e -> Log.d("Fail on send", "Failure " + e.message) }

        val chatRef=FirebaseDatabase.getInstance().getReference("ChatList").child(user.uid).child(receiverId)
        chatRef.child("chat_id").setValue(receiverId)

        val chatRef2=FirebaseDatabase.getInstance().getReference("ChatList").child(receiverId).child(user.uid)
        chatRef2.child("chat_id").setValue(user.uid)

    }

    private fun readChatList(){
        val reference=FirebaseDatabase.getInstance().reference
        reference.child("Chats").addValueEventListener(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onDataChange(snapshot: DataSnapshot) {
                data.clear()
                for(snap in snapshot.children){
                    val chat=snap.getValue(Chat::class.java)
                    if((chat?.sender==user.uid && chat?.receiver==receiverId)
                        || (chat?.sender==receiverId && chat?.receiver==user.uid )){
                        data.add(chat)
                    }
                }
                if(adapter!=null){
                    adapter?.notifyItemInserted(data.size-1)
                    recyclerView.layoutManager?.scrollToPosition(data.size-1)
                }else{
                    adapter= AdapterChat(applicationContext,data)
                    recyclerView.adapter=adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}