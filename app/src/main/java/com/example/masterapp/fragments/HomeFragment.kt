package com.example.masterapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.masterapp.MainActivity
import com.example.masterapp.R
import com.example.masterapp.databinding.FragmentHomeBinding
import com.example.masterapp.retrofit.RetroViewModel
import com.example.masterapp.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewmodel: AuthViewModel by activityViewModels()
    private val retroViewModel : RetroViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }

        binding.tvThirdPartyManufacturing.setOnClickListener {
            findNavController().navigate(R.id.partymanufacturingFragment)
        }

        binding.tvContactManufacturing.setOnClickListener {
            findNavController().navigate(R.id.contractmanufacturingFragment)
        }

        binding.tvAboutUs.setOnClickListener {
            findNavController().navigate(R.id.aboutusFragment)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.authState.collectLatest {
                if (it.isLoginSuccessful == true || viewmodel.isUserLoggedIn() == true) {
                    binding.userLogin.setOnClickListener {
                        findNavController().navigate(R.id.productTypeSelectionFragment)
                    }
                } else {
                    binding.userLogin.setOnClickListener {
                        findNavController().navigate(R.id.userloginFragment)
                    }
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.authState.collectLatest {
                if (it.isAdminLoggedIn == true || viewmodel.getUserEmail() == "pushp.hans1502@gmail.com") {
                    binding.adminLogin.setOnClickListener {
                        findNavController().navigate(R.id.loggedinAdminFragment)
                    }
                }
                else {
                    binding.adminLogin.setOnClickListener {
                        findNavController().navigate(R.id.adminloginFragment)
                    }
                }
            }
        }


    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).toolbarTitle.text = "Home"


    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}