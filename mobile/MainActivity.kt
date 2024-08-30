

package com.example.mygooglecalendar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("AppSettings", Context.MODE_PRIVATE)


        val webView = findViewById<WebView>(R.id.webview)
        val refreshButton = findViewById<Button>(R.id.refresh_button)
        val settingsButton = findViewById<Button>(R.id.settings_button)

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

        // Load saved settings or use defaults
        val url = sharedPreferences.getString("url", "<your_url_here>") ?: ""
        val username = sharedPreferences.getString("username", "<your_username>")
        val password = sharedPreferences.getString("password", "<your_password>")
        val auth = "Basic " + android.util.Base64.encodeToString("$username:$password".toByteArray(), android.util.Base64.NO_WRAP)

        webView.loadUrl(url, mapOf("Authorization" to auth))

        refreshButton.setOnClickListener {
            webView.loadUrl(url, mapOf("Authorization" to auth))
        }

        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}
