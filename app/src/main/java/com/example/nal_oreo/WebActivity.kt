package com.example.nal_oreo

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.nal_oreo.databinding.ActivityWebBinding

class WebActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Toolbar dan Tombol Back
        setSupportActionBar(binding.toolbarWeb)
        supportActionBar?.title = "Info SELLORA"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarWeb.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Muat WebView Info Skincare / Sellora
        binding.webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            loadUrl("https://id.wikipedia.org/wiki/Kosmetik")
        }
    }
}