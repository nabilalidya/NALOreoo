package com.example.nal_oreo.project_sellora

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nal_oreo.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class VerificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)

        // Ambil data dari SharedPreferences
        val sharedPref = getSharedPreferences("UserRegisterPref", Context.MODE_PRIVATE)
        val nama = sharedPref.getString("sp_nama", "") ?: ""
        val email = sharedPref.getString("sp_email", "") ?: ""
        val tglLahir = sharedPref.getString("sp_tgl_lahir", "") ?: ""
        val jk = sharedPref.getString("sp_jk", "") ?: ""
        val username = sharedPref.getString("sp_username", "") ?: ""
        val password = sharedPref.getString("sp_password", "") ?: ""
        val confirmPassword = sharedPref.getString("sp_confirm_password", "") ?: ""

        // Tampilkan seluruh isian di layar
        findViewById<TextView>(R.id.tvValNama).text = "Nama: $nama"
        findViewById<TextView>(R.id.tvValEmail).text = "Email: $email"
        findViewById<TextView>(R.id.tvValTglLahir).text = "Tanggal Lahir: $tglLahir"
        findViewById<TextView>(R.id.tvValJK).text = "Jenis Kelamin: $jk"
        findViewById<TextView>(R.id.tvValUsername).text = "Username: $username"
        findViewById<TextView>(R.id.tvValPassword).text = "Password: $password"
        findViewById<TextView>(R.id.tvValConfirmPassword).text = "Confirm Password: $confirmPassword"

        val btnSubmit = findViewById<MaterialButton>(R.id.btnSubmit)
        val btnKembali = findViewById<MaterialButton>(R.id.btnKembali)

        // Tombol Submit: Melakukan Validasi
        btnSubmit.setOnClickListener {
            val isNotEmpty = nama.isNotEmpty() && email.isNotEmpty() && tglLahir.isNotEmpty() &&
                    jk.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()
            val isPasswordMatch = password == confirmPassword

            if (!isNotEmpty) {
                Toast.makeText(this, "Validasi Gagal: Semua inputan tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            } else if (!isPasswordMatch) {
                Toast.makeText(this, "Validasi Gagal: Password dan Confirm Password harus sama!", Toast.LENGTH_SHORT).show()
            } else {
                // Tampilkan Dialog Registrasi Berhasil jika lolos validasi
                MaterialAlertDialogBuilder(this)
                    .setTitle("Registrasi Berhasil 🎉")
                    .setMessage("Data kamu telah tervalidasi. Silakan Login!")
                    .setPositiveButton("Ke Halaman Login") { _, _ ->
                        val intent = Intent(this, AuthActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                    .show()
            }
        }

        // Tombol Kembali: Kembali ke Halaman Registrasi untuk memperbaiki isi SP
        btnKembali.setOnClickListener {
            finish()
        }
    }
}