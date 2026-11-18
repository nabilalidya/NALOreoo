package com.example.nal_oreo.project_sellora

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.nal_oreo.R

data class TransaksiModel(
    val kodeOrder: String,
    val detail: String,
    val nominal: String
)

class TransaksiAdapter(
    private val context: Context,
    private val listTransaksi: List<TransaksiModel>
) : BaseAdapter() {

    override fun getCount(): Int = listTransaksi.size
    override fun getItem(position: Int): Any = listTransaksi[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_transaksi_home, parent, false)

        val item = listTransaksi[position]
        view.findViewById<TextView>(R.id.tvNamaTransaksi).text = item.kodeOrder
        view.findViewById<TextView>(R.id.tvDetailTransaksi).text = item.detail
        view.findViewById<TextView>(R.id.tvNominalTransaksi).text = item.nominal

        return view
    }
}