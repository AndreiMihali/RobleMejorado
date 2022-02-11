package com.example.roblemejorado.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.example.roblemejorado.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view= inflater.inflate(R.layout.fragment_home, container, false)
        webView=view.findViewById(R.id.webView)
        getLink()
        return view
    }

    private fun getLink(){
        val auth=FirebaseAuth.getInstance().currentUser
        val bd=FirebaseFirestore.getInstance()
        auth?.email.let {
            bd.collection("users").document(it!!).get()
                .addOnSuccessListener {it2->
                    webView.loadUrl(it2.getString("centro_estudios").toString())
                }
        }
    }


}
