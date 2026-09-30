package com.example.nal_oreo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nal_oreo.databinding.ActivityDetailSnackBinding

class DetailSnackActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailSnackBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSnackBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}