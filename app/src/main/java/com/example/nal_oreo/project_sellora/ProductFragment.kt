package com.example.nal_oreo.project_sellora

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.nal_oreo.R

class ProductFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product, container, false)

        val etSearch = view.findViewById<EditText>(R.id.etSearchProduct)
        val card1 = view.findViewById<View>(R.id.cardProduct1)
        val card2 = view.findViewById<View>(R.id.cardProduct2)
        val card3 = view.findViewById<View>(R.id.cardProduct3)

        etSearch?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().lowercase().trim()

                // Filter Card 1: Glow Serum
                if ("glow serum vitamin c".contains(query)) {
                    card1?.visibility = View.VISIBLE
                } else {
                    card1?.visibility = View.GONE
                }

                // Filter Card 2: Matte Lip Cream
                if ("matte lip cream rose".contains(query)) {
                    card2?.visibility = View.VISIBLE
                } else {
                    card2?.visibility = View.GONE
                }

                // Filter Card 3: Hydrating Sunscreen
                if ("hydrating sunscreen spf 50".contains(query)) {
                    card3?.visibility = View.VISIBLE
                } else {
                    card3?.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        return view
    }
}