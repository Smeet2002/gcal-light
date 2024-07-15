

package com.example.mygooglecalendar

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


        val webView = findViewById<WebView>(R.id.webview)
        val refreshButton = findViewById<Button>(R.id.refresh_button)

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

        val url = "https://<your_domain>/<your_page>"  // Replace with your URL
        val username = "<username>"   // Replace with your username
        val password = "<password>"   // Replace with your password
        val auth = "Basic " + android.util.Base64.encodeToString("$username:$password".toByteArray(), android.util.Base64.NO_WRAP)
        webView.loadUrl(url, mapOf("Authorization" to auth))

        refreshButton.setOnClickListener {
            webView.loadUrl(url, mapOf("Authorization" to auth))
        }
    }
}
