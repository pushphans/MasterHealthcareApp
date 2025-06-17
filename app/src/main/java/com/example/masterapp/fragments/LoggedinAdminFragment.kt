package com.example.masterapp.fragments

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.masterapp.MainActivity
import com.example.masterapp.R
import com.example.masterapp.StockManager
import com.example.masterapp.databinding.FragmentLoggedinAdminBinding
import com.example.masterapp.retrofit.AdminStockAdapter
import com.example.masterapp.retrofit.RetroViewModel
import com.example.masterapp.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class LoggedinAdminFragment : Fragment(R.layout.fragment_loggedin_admin) {

    private var _binding : FragmentLoggedinAdminBinding? = null
    private val binding get() = _binding!!

    private val viewModel : AuthViewModel by activityViewModels()
    private val retroViewModel : RetroViewModel by activityViewModels()
    private lateinit var adminAdapter : AdminStockAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoggedinAdminBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            findNavController().popBackStack(R.id.homeFragment, false)
        }

        binding.btnLogout.setOnClickListener {
            viewModel.signOut()
            viewModel.resetAuthState()
            findNavController().navigate(R.id.adminloginFragment)
        }


        StockManager.init(requireContext())

        setUpRecyclerView()

        if(retroViewModel.chemicalList.value.isEmpty()){
            retroViewModel.getChemicals()
        }
        lifecycleScope.launch {
            retroViewModel.chemicalList.collectLatest {
                adminAdapter.submitList(it)
            }
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
                        retroViewModel.chemicalList.collectLatest {
                            adminAdapter.submitList(it)
                        }
                    }
                }
                return true
            }
        })
    }

    private fun filterList(newText: String){
        lifecycleScope.launch {

            val query = newText.replace(Regex("[^A-Za-z0-9]"), "")
            retroViewModel.chemicalList.collectLatest {fullList ->
                val filterList = fullList.filter {
                    val cleanProductName = it.ProductName.replace(Regex("[^A-Za-z0-9]"), "").lowercase()
                    val cleanCasNo = it.CasNo.replace(Regex("[^A-Za-z0-9]"), "").lowercase()

                    cleanProductName.contains(query) || cleanCasNo.contains(query)
                }
                adminAdapter.submitList(filterList)
            }
        }
    }

    private fun setUpRecyclerView() {
        adminAdapter = AdminStockAdapter{item, isChecked -> }
        binding.rvAdmin.adapter = adminAdapter
        binding.rvAdmin.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).toolbarTitle.text = "Admin Fragment"
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}