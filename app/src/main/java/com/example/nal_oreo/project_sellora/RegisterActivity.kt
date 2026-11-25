package com.example.nal_oreo.project_sellora

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.nal_oreo.R
import com.example.nal_oreo.data.SupabaseClient
import com.example.nal_oreo.data.UserRequest
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etNama = findViewById<TextInputEditText>(R.id.etNama)
        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        val etTglLahir = findViewById<TextInputEditText>(R.id.etTglLahir)
        val rgJenisKelamin = findViewById<RadioGroup>(R.id.rgJenisKelamin)
        val etUsername = findViewById<TextInputEditText>(R.id.etUsername)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        val etConfirmPassword = findViewById<TextInputEditText>(R.id.etConfirmPassword)
        val btnSelanjutnya = findViewById<MaterialButton>(R.id.btnSelanjutnya)

        etTglLahir.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(this, { _, year, month, day ->
                etTglLahir.setText("$day/${month + 1}/$year")
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        btnSelanjutnya.setOnClickListener {
            val selectedGenderId = rgJenisKelamin.checkedRadioButtonId
            val rbGender = if (selectedGenderId != -1) findViewById<RadioButton>(selectedGenderId) else null

            val nama = etNama.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val tglLahir = etTglLahir.text.toString().trim()
            val jenisKelamin = rbGender?.text?.toString() ?: ""
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()

            if (nama.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Lengkapi semua field wajib!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Konfirmasi password tidak cocok!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sharedPref = getSharedPreferences("UserRegisterPref", Context.MODE_PRIVATE)
            sharedPref.edit().apply {
                putString("sp_nama", nama)
                putString("sp_email", email)
                putString("sp_tgl_lahir", tglLahir)
                putString("sp_jk", jenisKelamin)
                putString("sp_username", username)
                putString("sp_password", password)
                putString("sp_confirm_password", confirmPassword)
                apply()
            }

            btnSelanjutnya.isEnabled = false
            btnSelanjutnya.text = "Menyimpan..."

            lifecycleScope.launch {
                try {
                    val userPayload = UserRequest(nama, email, tglLahir, jenisKelamin, username, password)
                    val response = SupabaseClient.apiService.registerUser(userPayload)

                    if (response.isSuccessful) {
                        Toast.makeText(this@RegisterActivity, "Registrasi berhasil disimpan ke Supabase!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterActivity, VerificationActivity::class.java))
                    } else {
                        Toast.makeText(this@RegisterActivity, "Gagal register: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@RegisterActivity, "Error jaringan: ${e.message}", Toast.LENGTH_SHORT).show()
                } finally {
                    btnSelanjutnya.isEnabled = true
                    btnSelanjutnya.text = "Selanjutnya"
                }
            }
        }
    }
}