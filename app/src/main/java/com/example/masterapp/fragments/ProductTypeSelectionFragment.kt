package com.example.masterapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.masterapp.MainActivity
import com.example.masterapp.R
import com.example.masterapp.databinding.FragmentProductTypeSelectionBinding
import com.example.masterapp.viewmodel.AuthViewModel

class ProductTypeSelectionFragment : Fragment(R.layout.fragment_product_type_selection) {

    private var _binding : FragmentProductTypeSelectionBinding? = null
    private val binding get() = _binding!!
    private val viewmodel : AuthViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductTypeSelectionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            findNavController().popBackStack(R.id.homeFragment, false)
        }

        binding.cvAllProducts.setOnClickListener {
            Log.d("ClickHandling", "Card is clicked")
            findNavController().navigate(R.id.allProductsListFragment)
        }

        binding.cvReadyProducts.setOnClickListener {
            findNavController().navigate(R.id.loggedinuserFragment)

        }

        binding.btnLogout.setOnClickListener {
            viewmodel.signOut()
            viewmodel.resetAuthState()
            findNavController().popBackStack(R.id.userloginFragment, false)
            findNavController().navigate(R.id.userloginFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).toolbarTitle.text = "Product Lists"
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}