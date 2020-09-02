package com.example.benzoandroidtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.webkit.WebView
import android.webkit.WebViewClient

class SubActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val url: String = this.intent.getStringExtra("url")
        val webView = WebView(this)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl(url)

        val layout = FrameLayout(this)
        layout.addView(webView)
        this.setContentView(layout)
    }
}
