package com.example.nal_oreo.project_sellora

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nal_oreo.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
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

        // 1. Tampilkan DatePickerDialog saat Tanggal Lahir diklik
        etTglLahir.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                etTglLahir.setText("$selectedDay/${selectedMonth + 1}/$selectedYear")
            }, year, month, day)
            datePickerDialog.show()
        }

        // 2. Klik tombol Selanjutnya untuk menyimpan ke SharedPreferences
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

            // Simpan seluruh isian ke SharedPreferences
            val sharedPref = getSharedPreferences("UserRegisterPref", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("sp_nama", nama)
            editor.putString("sp_email", email)
            editor.putString("sp_tgl_lahir", tglLahir)
            editor.putString("sp_jk", jenisKelamin)
            editor.putString("sp_username", username)
            editor.putString("sp_password", password)
            editor.putString("sp_confirm_password", confirmPassword)
            editor.apply()

            // Lanjut ke Halaman Verifikasi
            startActivity(Intent(this, VerificationActivity::class.java))
        }
    }
}