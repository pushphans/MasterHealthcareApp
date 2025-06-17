package com.example.masterapp.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.masterapp.MainActivity
import com.example.masterapp.R
import com.example.masterapp.databinding.FragmentContactSalesBinding


class ContactSalesFragment : Fragment(R.layout.fragment_contact_sales) {

    private var _binding : FragmentContactSalesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactSalesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        binding.btnContact1.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:8580881072 ")
            }
            startActivity(intent)
        }

        binding.btnContact2.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:8219758491")
            }
            startActivity(intent)
        }

        binding.btnContact3 .setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:7018099664")
            }
            startActivity(intent)
        }

    }


    override fun onResume() {
        super.onResume()
        (activity as MainActivity).toolbarTitle.text = "Call sales"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}