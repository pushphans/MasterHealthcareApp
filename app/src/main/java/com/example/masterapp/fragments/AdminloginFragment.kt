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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.masterapp.MainActivity
import com.example.masterapp.R
import com.example.masterapp.databinding.FragmentAdminloginBinding
import com.example.masterapp.viewmodel.AuthViewModel
import com.google.common.base.MoreObjects.ToStringHelper
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AdminloginFragment : Fragment(R.layout.fragment_adminlogin) {

    private var _binding: FragmentAdminloginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminloginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            findNavController().popBackStack(R.id.homeFragment, false)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.authState.collectLatest {
                if (it.isAdminLoggedIn == true) {
                    findNavController().navigate(R.id.loggedinAdminFragment)
                    viewModel.resetAuthState()
                }
                else if (it.errorMessage != null) {
                    Toast.makeText(
                        requireContext(),
                        "Error : ${it.errorMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.etEmail.text?.clear()
                    binding.etPassword.text?.clear()
                    viewModel.resetAuthState()
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.adminSignIn(email, password)
            }
            else{
                Toast.makeText(requireContext(), "Details are incomplete", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).toolbarTitle.text = "Admin Login"
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}