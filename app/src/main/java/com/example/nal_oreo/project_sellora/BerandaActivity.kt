package com.example.nal_oreo.project_sellora

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nal_oreo.WebActivity
import com.example.nal_oreo.databinding.ActivityBerandaBinding

class BerandaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBerandaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBerandaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
        val username = sharedPref.getString("username", "nabila")

        binding.tvWelcomeUser.text = "Halo, $username! 👋"

        // Tombol menuju Info Sellora (Web)
        binding.btnInfoSellora.setOnClickListener {
            startActivity(Intent(this, WebActivity::class.java))
        }

        // Tombol menuju Profil Developer
        binding.btnProfilDeveloper.setOnClickListener {
            startActivity(Intent(this, ProfileDeveloperActivity::class.java))
        }

        // Tombol Logout untuk mereset session login
        binding.btnLogout.setOnClickListener {
            val editor = sharedPref.edit()
            editor.clear()
            editor.apply()

            val intent = Intent(this, WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}