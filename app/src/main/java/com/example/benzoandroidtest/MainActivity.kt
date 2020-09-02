package com.example.benzoandroidtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import kotlinx.android.synthetic.main.activity_main.button1
import kotlinx.android.synthetic.main.activity_main.button2
import kotlinx.android.synthetic.main.activity_main.editText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_main)

        this.button1.setOnClickListener {
            val url: String = this.editText.text.toString()
            val intent = Intent(this.application, SubActivity::class.java)
            intent.putExtra("url", url)
            this.startActivity(intent)
        }

        this.button2.setOnClickListener {
            val url: String = this.editText.text.toString()
            val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
            val intent: CustomTabsIntent = builder.build()
            intent.intent.setPackage("com.android.chrome")
            intent.launchUrl(this, Uri.parse(url))
        }
    }
}
