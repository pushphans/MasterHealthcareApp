package com.example.masterapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.masterapp.MainActivity
import com.example.masterapp.R
import com.example.masterapp.StockManager
import com.example.masterapp.databinding.FragmentLoggedinuserBinding
import com.example.masterapp.databinding.FragmentPartymanufacturingBinding
import com.example.masterapp.retrofit.ListDataClass
import com.example.masterapp.retrofit.RetroAdapter
import com.example.masterapp.retrofit.RetroViewModel
import com.example.masterapp.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class LoggedinuserFragment : Fragment(R.layout.fragment_loggedinuser) {

    private var _binding : FragmentLoggedinuserBinding? = null
    private val binding get() = _binding!!

    private val retroViewmodel : RetroViewModel by activityViewModels()
    private lateinit var adapter : RetroAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoggedinuserBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        StockManager.init(requireContext())

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            findNavController().popBackStack(R.id.productTypeSelectionFragment, false)
        }


        binding.btnProceed.setOnClickListener {
            findNavController().navigate(R.id.contactSalesFragment)
        }


        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != null){
                    filterList(newText)
                }
                else{
                    lifecycleScope.launch {
                        retroViewmodel.chemicalList.collectLatest {
                            adapter.submitList(it)
                        }
                    }
                }
                return true
            }
        })


        if(retroViewmodel.chemicalList.value.isEmpty()) {
            retroViewmodel.getChemicals()
        }


        adapter = RetroAdapter()
        binding.rvUser.adapter = adapter
        binding.rvUser.layoutManager = LinearLayoutManager(requireContext())



        lifecycleScope.launch {
            retroViewmodel.chemicalList.collectLatest {
                Log.d("myTag", "Fragment : List size is ${it.size}")
                adapter.submitList(it)
            }
        }
    }


    private fun filterList(newText: String){
        lifecycleScope.launch {

            val query = newText.replace(Regex("[^A-Za-z0-9]"), "")
            retroViewmodel.chemicalList.collectLatest {fullList ->
                val filterList = fullList.filter {
                    val cleanProductName = it.ProductName.replace(Regex("[^A-Za-z0-9]"), "").lowercase()
                    val cleanCasNo = it.CasNo.replace(Regex("[^A-Za-z0-9]"), "").lowercase()

                    cleanProductName.contains(query) || cleanCasNo.contains(query)
                }
                adapter.submitList(filterList)
            }
        }
    }


    override fun onResume() {
        super.onResume()


        (activity as MainActivity).toolbarTitle.text = "Products in Stock"
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}