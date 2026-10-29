package com.example.nal_oreo.project_sellora

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nal_oreo.WebActivity
import com.example.nal_oreo.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tombol Sign In -> Ke Halaman Login (AuthActivity)
        binding.btnSignIn.setOnClickListener {
            startActivity(Intent(this, AuthActivity::class.java))
        }

        // Tombol Info Sellora -> Ke WebActivity
        binding.btnInfoSellora.setOnClickListener {
            startActivity(Intent(this, WebActivity::class.java))
        }
    }
}