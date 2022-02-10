package com.example.roblemejorado.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.example.roblemejorado.R

class HomeFragment : Fragment() {

    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view= inflater.inflate(R.layout.fragment_home, container, false)
        webView=view.findViewById(R.id.webView)
        webView.loadUrl("https://site.educa.madrid.org/ies.juandelacierva.madrid/")
        return view
    }
}
