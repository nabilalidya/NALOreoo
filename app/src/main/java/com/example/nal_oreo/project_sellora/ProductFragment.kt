package com.example.nal_oreo.project_sellora

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nal_oreo.R
import com.example.nal_oreo.data.ProductModel
import com.example.nal_oreo.data.SupabaseClient
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class ProductFragment : Fragment() {

    private lateinit var productAdapter: ProductAdapter
    private var fullProductList = listOf<ProductModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product, container, false)

        val etSearch = view.findViewById<EditText>(R.id.etSearchProduct)
        val rvProducts = view.findViewById<RecyclerView>(R.id.rvProducts)
        val btnAddProduct = view.findViewById<MaterialButton>(R.id.btnAddProduct)

        productAdapter = ProductAdapter(emptyList()) { product ->
            showEditDeleteDialog(product)
        }

        rvProducts.layoutManager = LinearLayoutManager(requireContext())
        rvProducts.adapter = productAdapter

        btnAddProduct.setOnClickListener {
            showProductFormDialog(existingProduct = null)
        }

        loadProducts()

        etSearch?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().lowercase().trim()
                val filteredList = fullProductList.filter {
                    it.name.lowercase().contains(query)
                }
                productAdapter.updateData(filteredList)
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        return view
    }

    private fun loadProducts() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                fullProductList = SupabaseClient.apiService.getProducts()
                productAdapter.updateData(fullProductList)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Gagal memuat produk: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showEditDeleteDialog(product: ProductModel) {
        val options = arrayOf("Edit Produk (PATCH)", "Hapus Produk (DELETE)")
        AlertDialog.Builder(requireContext())
            .setTitle(product.name)
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showProductFormDialog(product)
                    1 -> deleteProduct(product)
                }
            }
            .show()
    }

    private fun showProductFormDialog(existingProduct: ProductModel?) {
        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 24, 48, 12)
        }

        val etName = EditText(requireContext()).apply {
            hint = "Nama Produk (contoh: Serum B5)"
            setText(existingProduct?.name ?: "")
        }
        val etStock = EditText(requireContext()).apply {
            hint = "Stok (contoh: Stok: 50 Pcs)"
            setText(existingProduct?.stock ?: "")
        }
        val etPrice = EditText(requireContext()).apply {
            hint = "Harga (contoh: Rp110.000)"
            setText(existingProduct?.price ?: "")
        }

        layout.addView(etName)
        layout.addView(etStock)
        layout.addView(etPrice)

        val isEdit = existingProduct != null
        AlertDialog.Builder(requireContext())
            .setTitle(if (isEdit) "Edit Produk" else "Tambah Produk Baru")
            .setView(layout)
            .setPositiveButton("Simpan") { _, _ ->
                val name = etName.text.toString().trim()
                val stock = etStock.text.toString().trim()
                val price = etPrice.text.toString().trim()

                if (name.isEmpty() || stock.isEmpty() || price.isEmpty()) {
                    Toast.makeText(requireContext(), "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                viewLifecycleOwner.lifecycleScope.launch {
                    try {
                        val payload = ProductModel(name = name, stock = stock, price = price)
                        val response = if (isEdit) {
                            SupabaseClient.apiService.updateProduct("eq.${existingProduct!!.id}", payload)
                        } else {
                            SupabaseClient.apiService.addProduct(payload)
                        }

                        if (response.isSuccessful) {
                            Toast.makeText(requireContext(), if (isEdit) "Produk diperbarui!" else "Produk ditambahkan!", Toast.LENGTH_SHORT).show()
                            loadProducts()
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

    private fun deleteProduct(product: ProductModel) {
        val id = product.id ?: return
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = SupabaseClient.apiService.deleteProduct("eq.$id")
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Produk dihapus!", Toast.LENGTH_SHORT).show()
                    loadProducts()
                } else {
                    Toast.makeText(requireContext(), "Gagal hapus: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}