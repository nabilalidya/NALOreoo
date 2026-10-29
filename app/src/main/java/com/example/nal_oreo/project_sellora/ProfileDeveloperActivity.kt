package com.example.nal_oreo.project_sellora

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nal_oreo.databinding.ActivityProfileDeveloperBinding

class ProfileDeveloperActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileDeveloperBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDeveloperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Setup Toolbar & Tombol Back
        setSupportActionBar(binding.toolbarProfile)
        supportActionBar?.title = "Profil Developer"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarProfile.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // 2. Fitur Baru Pertemuan 9: Tombol Bagikan Profil Developer via Implicit Intent
        binding.btnShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "Profil Pengembang SELLORA")
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Halo! Cek profil pengembang aplikasi SELLORA: Nabila Lidya Ananda (@nabilalidya) - Android Developer & Business Analyst."
                )
            }
            startActivity(Intent.createChooser(shareIntent, "Bagikan via:"))
        }
    }
}