package com.example.masterapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.masterapp.MainActivity
import com.example.masterapp.R
import com.example.masterapp.databinding.FragmentContractmanufacturingBinding

class ContractmanufacturingFragment : Fragment(R.layout.fragment_contractmanufacturing) {

    private var _binding : FragmentContractmanufacturingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContractmanufacturingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            findNavController().popBackStack(R.id.homeFragment, false)
        }
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).toolbarTitle.text = "Contract Manufacturing"
        (activity as MainActivity).toolbarTitle.setTextSize(25f)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}