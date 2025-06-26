package com.example.masterapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.masterapp.MainActivity
import com.example.masterapp.R
import com.example.masterapp.databinding.FragmentAllProductsListBinding
import com.example.masterapp.retrofit.AllProductsAdapter
import com.example.masterapp.retrofit.RetroViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class AllProductsListFragment : Fragment(R.layout.fragment_all_products_list) {

    private var _binding : FragmentAllProductsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter : AllProductsAdapter
    private val viewmodel : RetroViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllProductsListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })

        if(viewmodel.allProductsList.value.isEmpty()){
            viewmodel.allProducts()
        }

        adapter = AllProductsAdapter()
        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(requireContext())


        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.allProductsList.collectLatest {
                adapter.submitList(it)
            }
        }

        binding.btnEnquire.setOnClickListener {
            findNavController().navigate(R.id.contactSalesFragment)
        }
    }


    override fun onResume() {
        super.onResume()
        (activity as MainActivity).toolbarTitle.text = "All Products"
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}