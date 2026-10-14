package com.example.nal_oreo.project_sellora

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nal_oreo.databinding.ActivityProfileDeveloperBinding

class ProfileDeveloperActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileDeveloperBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDeveloperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Toolbar & Tombol Back
        setSupportActionBar(binding.toolbarProfile)
        supportActionBar?.title = "Profil Developer"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarProfile.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}