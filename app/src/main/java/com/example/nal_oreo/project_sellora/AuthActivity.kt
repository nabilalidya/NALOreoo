package com.example.nal_oreo.project_sellora

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.nal_oreo.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPrefSession = getSharedPreferences("user_pref", MODE_PRIVATE)
        val sharedPrefRegister = getSharedPreferences("UserRegisterPref", MODE_PRIVATE)

        // Tombol Login
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // Ambil data registrasi yang tersimpan di SharedPreferences
            val savedUsername = sharedPrefRegister.getString("sp_username", "") ?: ""
            val savedPassword = sharedPrefRegister.getString("sp_password", "") ?: ""

            // Rule 1: Jika username = password
            val isRule1 = username.isNotEmpty() && username == password

            // Rule 2: Jika username = username di SP dan password = password di SP
            val isRule2 = username.isNotEmpty() && password.isNotEmpty() &&
                    username == savedUsername && password == savedPassword

            if (isRule1 || isRule2) {
                // Simpan Sesi Login
                val editor = sharedPrefSession.edit()
                editor.putBoolean("isLogin", true)
                editor.putString("username", username)
                editor.apply()

                // Masuk ke Beranda SELLORA setelah login
                val intent = Intent(this, BerandaActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Peringatan")
                    .setMessage("Username atau Password salah! (Aturan: Username & Password harus sama, ATAU sesuai data Registrasi)")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }

        // Navigasi ke Halaman Registrasi (Pastikan kamu punya view/button di layout XML dengan ID btnRegister atau sejenisnya)
        binding.btnRegister?.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}