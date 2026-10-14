package com.example.nal_oreo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nal_oreo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMyProject.setOnClickListener {
            startActivity(Intent(this, DetailSnackActivity::class.java))
        }

        binding.btnWebsite.setOnClickListener {
            startActivity(Intent(this, WebActivity::class.java))
        }
    }
}