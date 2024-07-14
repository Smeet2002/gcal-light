package com.example.mygooglecalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val webView = findViewById<WebView>(R.id.webview)

        webView.settings.javaScriptEnabled = false
        webView.settings.useWideViewPort = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false



        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                return false
            }


        }

        val url = "<URL>"  // Replace with your URL
        val username = "<USERNAME>"   // Replace with your username
        val password = "<PASSWORD>"   // Replace with your password
        val auth = "Basic " + android.util.Base64.encodeToString("$username:$password".toByteArray(), android.util.Base64.NO_WRAP)
        webView.loadUrl(url, mapOf("Authorization" to auth))
    }
}
