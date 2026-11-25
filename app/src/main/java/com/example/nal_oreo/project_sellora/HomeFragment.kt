package com.example.nal_oreo.project_sellora

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.nal_oreo.R
import com.example.nal_oreo.WebActivity
import com.example.nal_oreo.data.SupabaseClient
import com.example.nal_oreo.data.TransaksiSupabaseModel
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var customAdapter: TransaksiAdapter
    private var dataTransaksi = listOf<TransaksiSupabaseModel>()

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
        val btnAddTransaksi = view.findViewById<MaterialButton>(R.id.btnAddTransaksi)
        val btnInfoSellora = view.findViewById<Button>(R.id.btnInfoSellora)
        val btnProfilDeveloper = view.findViewById<Button>(R.id.btnProfilDeveloper)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        val sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "Nabila")
        tvWelcomeUser?.text = "Halo, $username 👋"

        customAdapter = TransaksiAdapter(requireContext(), emptyList())
        lvTransaksiTerbaru?.adapter = customAdapter

        loadTransactions()

        btnAddTransaksi?.setOnClickListener {
            showTransaksiFormDialog(existing = null)
        }

        lvTransaksiTerbaru?.setOnItemClickListener { _, _, position, _ ->
            val terpilih = dataTransaksi[position]
            val options = arrayOf("Edit Transaksi (PATCH)", "Hapus Transaksi (DELETE)")
            AlertDialog.Builder(requireContext())
                .setTitle(terpilih.kodeOrder)
                .setItems(options) { _, which ->
                    when (which) {
                        0 -> showTransaksiFormDialog(terpilih)
                        1 -> deleteTransaction(terpilih)
                    }
                }
                .show()
        }

        btnInfoSellora?.setOnClickListener {
            startActivity(Intent(requireContext(), WebActivity::class.java))
        }

        btnProfilDeveloper?.setOnClickListener {
            startActivity(Intent(requireContext(), ProfileDeveloperActivity::class.java))
        }

        btnLogout?.setOnClickListener {
            sharedPref.edit().clear().apply()
            val intent = Intent(requireContext(), WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun loadTransactions() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                dataTransaksi = SupabaseClient.apiService.getTransactions()
                customAdapter.updateData(dataTransaksi)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Gagal memuat transaksi: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showTransaksiFormDialog(existing: TransaksiSupabaseModel?) {
        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 24, 48, 12)
        }

        val etKode = EditText(requireContext()).apply {
            hint = "Kode Order (contoh: Order #SLR-105)"
            setText(existing?.kodeOrder ?: "")
        }
        val etDetail = EditText(requireContext()).apply {
            hint = "Detail (contoh: Serum (1x) • Selesai)"
            setText(existing?.detail ?: "")
        }
        val etNominal = EditText(requireContext()).apply {
            hint = "Nominal (contoh: +Rp120.000)"
            setText(existing?.nominal ?: "")
        }

        layout.addView(etKode)
        layout.addView(etDetail)
        layout.addView(etNominal)

        val isEdit = existing != null
        AlertDialog.Builder(requireContext())
            .setTitle(if (isEdit) "Edit Transaksi" else "Tambah Transaksi Baru")
            .setView(layout)
            .setPositiveButton("Simpan") { _, _ ->
                val kode = etKode.text.toString().trim()
                val detail = etDetail.text.toString().trim()
                val nominal = etNominal.text.toString().trim()

                if (kode.isEmpty() || detail.isEmpty() || nominal.isEmpty()) {
                    Toast.makeText(requireContext(), "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                viewLifecycleOwner.lifecycleScope.launch {
                    try {
                        val payload = TransaksiSupabaseModel(kodeOrder = kode, detail = detail, nominal = nominal)
                        val response = if (isEdit) {
                            SupabaseClient.apiService.updateTransaction("eq.${existing!!.id}", payload)
                        } else {
                            SupabaseClient.apiService.addTransaction(payload)
                        }

                        if (response.isSuccessful) {
                            Toast.makeText(requireContext(), if (isEdit) "Transaksi diperbarui!" else "Transaksi ditambahkan!", Toast.LENGTH_SHORT).show()
                            loadTransactions()
                        } else {
                            Toast.makeText(requireContext(), "Gagal: ${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun deleteTransaction(transaksi: TransaksiSupabaseModel) {
        val id = transaksi.id ?: return
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = SupabaseClient.apiService.deleteTransaction("eq.$id")
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Transaksi dihapus!", Toast.LENGTH_SHORT).show()
                    loadTransactions()
                } else {
                    Toast.makeText(requireContext(), "Gagal hapus: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}