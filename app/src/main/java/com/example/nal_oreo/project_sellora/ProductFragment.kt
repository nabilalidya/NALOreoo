package com.example.nal_oreo.project_sellora

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nal_oreo.R

class ProductFragment : Fragment() {

    private lateinit var productAdapter: ProductAdapter
    private val fullProductList = listOf(
        ProductSellora("Glow Serum Vitamin C ✨", "Stok: 45 Pcs", "Rp120.000"),
        ProductSellora("Matte Lip Cream Rose 💋", "Stok: 120 Pcs", "Rp75.000"),
        ProductSellora("Hydrating Sunscreen SPF 50 ☀️", "Stok: 80 Pcs", "Rp95.000"),
        ProductSellora("Micellar Cleansing Water 💧", "Stok: 64 Pcs", "Rp58.000"),
        ProductSellora("Night Repair Moisturizer 🌙", "Stok: 32 Pcs", "Rp135.000")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product, container, false)

        val etSearch = view.findViewById<EditText>(R.id.etSearchProduct)
        val rvProducts = view.findViewById<RecyclerView>(R.id.rvProducts)

        productAdapter = ProductAdapter(fullProductList) { product ->
            Toast.makeText(requireContext(), "${product.name} (${product.price})", Toast.LENGTH_SHORT).show()
        }

        rvProducts.layoutManager = LinearLayoutManager(requireContext())
        rvProducts.adapter = productAdapter

        etSearch?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().lowercase().trim()
                val filteredList = fullProductList.filter {
                    it.name.lowercase().contains(query)
                }
                productAdapter.filterList(filteredList)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        return view
    }
}