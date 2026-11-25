package com.example.nal_oreo.project_sellora

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.nal_oreo.R
import com.google.android.material.button.MaterialButton

data class OnboardItem(val title: String, val desc: String, val iconRes: Int)

class OnboardingActivity : AppCompatActivity() {

    private val items = listOf(
        OnboardItem(
            "Kelola Produk Lebih Mudah",
            "Atur stok, harga, dan katalog produk jualan kamu dalam satu genggaman dengan Sellora.",
            R.drawable.ic_sellora_logo
        ),
        OnboardItem(
            "Pantau Penjualan Real-Time",
            "Lihat ringkasan omzet, total transaksi, dan performa toko secara instan kapan saja.",
            R.drawable.ic_sellora_logo
        ),
        OnboardItem(
            "Tingkatkan Omzet Toko Skincare",
            "Analisis tren pelanggan aktif dan kembangkan penjualan produk skincare & kecantikan kamu bersama Sellora.",
            R.drawable.ic_sellora_logo
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val vpOnboarding = findViewById<ViewPager2>(R.id.vpOnboarding)
        val layoutDots = findViewById<LinearLayout>(R.id.layoutDots)
        val btnNext = findViewById<MaterialButton>(R.id.btnNextOnboard)
        val tvSkip = findViewById<TextView>(R.id.tvSkip)

        vpOnboarding.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_onboarding_sellora, parent, false)
                return object : RecyclerView.ViewHolder(v) {}
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val item = items[position]
                holder.itemView.findViewById<ImageView>(R.id.ivOnboardImage).setImageResource(item.iconRes)
                holder.itemView.findViewById<TextView>(R.id.tvOnboardTitle).text = item.title
                holder.itemView.findViewById<TextView>(R.id.tvOnboardDesc).text = item.desc
            }

            override fun getItemCount(): Int = items.size
        }

        fun updateDots(position: Int) {
            layoutDots.removeAllViews()
            for (i in items.indices) {
                val dot = TextView(this).apply {
                    text = "● "
                    textSize = 16f
                    setTextColor(if (i == position) Color.parseColor("#00A896") else Color.parseColor("#CBD5E1"))
                }
                layoutDots.addView(dot)
            }
            btnNext.text = if (position == items.lastIndex) "Mulai Sekarang" else "Selanjutnya"
        }

        updateDots(0)

        vpOnboarding.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateDots(position)
            }
        })

        fun finishOnboarding() {
            val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
            sharedPref.edit().putBoolean("hasSeenOnboard", true).apply()
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }

        btnNext.setOnClickListener {
            if (vpOnboarding.currentItem < items.lastIndex) {
                vpOnboarding.currentItem += 1
            } else {
                finishOnboarding()
            }
        }

        tvSkip.setOnClickListener { finishOnboarding() }
    }
}