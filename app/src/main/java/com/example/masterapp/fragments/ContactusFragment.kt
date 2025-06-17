package com.example.masterapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import com.example.masterapp.MainActivity
import com.example.masterapp.R
import com.example.masterapp.databinding.FragmentContactusBinding


class ContactusFragment : Fragment(R.layout.fragment_contactus) {
    private var _binding : FragmentContactusBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactusBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            findNavController().popBackStack(R.id.homeFragment, false)
        }

        binding.btnOkay.setOnClickListener {
            findNavController().navigate(R.id.action_contactusFragment_to_mapFragment)
        }

        binding.btnNo.setOnClickListener {
            Toast.makeText(requireContext(), "Getting back to the home page", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack(R.id.homeFragment, false)
        }

    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).toolbarTitle.text = "Contact Us"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}