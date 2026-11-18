package com.example.nal_oreo.project_sellora

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.nal_oreo.R
import com.example.nal_oreo.WebActivity

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvWelcomeUser = view.findViewById<TextView>(R.id.tvWelcomeUser)
        val lvTransaksiTerbaru = view.findViewById<ListView>(R.id.lvTransaksiTerbaru)
        val btnInfoSellora = view.findViewById<Button>(R.id.btnInfoSellora)
        val btnProfilDeveloper = view.findViewById<Button>(R.id.btnProfilDeveloper)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        val sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "Nabila")

        tvWelcomeUser?.text = "Halo, $username 👋"

        // Terapkan CustomAdapter ke ListView
        val dataTransaksi = listOf(
            TransaksiModel("Order #SLR-104", "Glow Serum Vitamin C (2x) • Selesai", "+Rp240.000"),
            TransaksiModel("Order #SLR-103", "Matte Lip Cream Rose (1x) • Dikirim", "+Rp75.000"),
            TransaksiModel("Order #SLR-102", "Hydrating Sunscreen SPF 50 • Selesai", "+Rp95.000")
        )
        val customAdapter = TransaksiAdapter(requireContext(), dataTransaksi)
        lvTransaksiTerbaru?.adapter = customAdapter

        lvTransaksiTerbaru?.setOnItemClickListener { _, _, position, _ ->
            val terpilih = dataTransaksi[position]
            Toast.makeText(requireContext(), "${terpilih.kodeOrder}: ${terpilih.nominal}", Toast.LENGTH_SHORT).show()
        }

        // Buka WebActivity
        btnInfoSellora?.setOnClickListener {
            startActivity(Intent(requireContext(), WebActivity::class.java))
        }

        // Buka ProfileDeveloperActivity
        btnProfilDeveloper?.setOnClickListener {
            startActivity(Intent(requireContext(), ProfileDeveloperActivity::class.java))
        }

        // Logout
        btnLogout?.setOnClickListener {
            val editor = sharedPref.edit()
            editor.clear()
            editor.apply()

            val intent = Intent(requireContext(), WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }
    }
}