package com.example.benzoandroidtest

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.net.http.SslError
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.SslErrorHandler
import android.webkit.PermissionRequest
import androidx.core.content.ContextCompat

class SubActivity : AppCompatActivity() {
    private val PERMISSION_REQUEST_CAMERA: Int = 0x01

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestPermissionAndCreateWebView()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == this.PERMISSION_REQUEST_CAMERA) {
            var isEnableCamera = false
            for(i: Int in permissions.indices) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) { continue }
                if (permissions[i] == Manifest.permission.CAMERA) { isEnableCamera = true }
            }
            // パーミッションを獲得したらもう一度パーミッションを確認する
            if (isEnableCamera) { this.requestPermissionAndCreateWebView() }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun requestPermissionAndCreateWebView() {
        val permission: Int = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (permission == PackageManager.PERMISSION_GRANTED) {
            // 必要なパーミッションを持っている
            this.createWebView()
        } else if (this.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            // パーミッション許可再確認
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setMessage("Permission Check")
            builder.setPositiveButton(android.R.string.ok) { _, _ -> this.requestPermissions(arrayOf(Manifest.permission.CAMERA), this.PERMISSION_REQUEST_CAMERA) }
            builder.setNegativeButton(android.R.string.cancel) { _, _ -> }
            builder.show()
        } else {
            // 最初のパーミッション許可確認
            this.requestPermissions(arrayOf(Manifest.permission.CAMERA), this.PERMISSION_REQUEST_CAMERA)
        }
    }

    private fun createWebView() {
        val url: String = this.intent.getStringExtra("url")
        val webView = WebView(this)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.mediaPlaybackRequiresUserGesture = false
        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
                handler.proceed()
            }
        }
        webView.webChromeClient = object : WebChromeClient() {
            override fun onPermissionRequest(request: PermissionRequest) {
                request.grant(request.resources)
            }
        }
        webView.loadUrl(url)

        val layout = FrameLayout(this)
        layout.addView(webView)
        this.setContentView(layout)
    }
}
