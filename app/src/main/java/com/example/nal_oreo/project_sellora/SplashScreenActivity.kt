package com.example.nal_oreo.project_sellora

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.nal_oreo.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)

        lifecycleScope.launch {
            delay(2000)

            val isLogin = sharedPref.getBoolean("isLogin", false)

            // Jika sudah login -> Ke Beranda SELLORA
            // Jika belum login -> Ke Welcome SELLORA
            val targetActivity = if (isLogin) {
                BerandaActivity::class.java
            } else {
                WelcomeActivity::class.java
            }

            startActivity(Intent(this@SplashScreenActivity, targetActivity))
            finish()
        }
    }
}