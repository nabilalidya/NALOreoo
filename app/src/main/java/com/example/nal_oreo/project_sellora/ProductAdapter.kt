package com.example.nal_oreo.project_sellora

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nal_oreo.R

data class ProductSellora(
    val name: String,
    val stock: String,
    val price: String
)

class ProductAdapter(
    private var productList: List<ProductSellora>,
    private val onItemClick: (ProductSellora) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvProductName)
        val tvStock: TextView = itemView.findViewById(R.id.tvProductStock)
        val tvPrice: TextView = itemView.findViewById(R.id.tvProductPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_sellora, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.tvName.text = product.name
        holder.tvStock.text = product.stock
        holder.tvPrice.text = product.price
        holder.itemView.setOnClickListener { onItemClick(product) }
    }

    override fun getItemCount(): Int = productList.size

    fun filterList(filtered: List<ProductSellora>) {
        productList = filtered
        notifyDataSetChanged()
    }
}