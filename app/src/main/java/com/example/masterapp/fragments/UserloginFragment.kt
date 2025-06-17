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
import com.example.masterapp.databinding.FragmentHomeBinding
import com.example.masterapp.databinding.FragmentUserloginBinding
import com.example.masterapp.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserloginFragment : Fragment(R.layout.fragment_userlogin) {

    private var _binding: FragmentUserloginBinding? = null
    private val binding get() = _binding!!

    private val viewmodel: AuthViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUserloginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack(R.id.homeFragment, false)
        }

        lifecycleScope.launch {
            viewmodel.authState.collectLatest {
                if (it.isLoginSuccessful == true) {
                    findNavController().navigate(R.id.productTypeSelectionFragment)
                    viewmodel.resetAuthState()
                } else if (it.errorMessage != null) {
                    Toast.makeText(requireContext(), "error : ${it.errorMessage}", Toast.LENGTH_SHORT).show()

                    findNavController().navigate(R.id.homeFragment)

                    binding.etEmail.text?.clear()
                    binding.etPassword.text.clear()
                    viewmodel.resetAuthState()
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewmodel.signIn(email, password)
            }
            else{
                Toast.makeText(requireContext(), "Details are incomplete", Toast.LENGTH_SHORT).show()
            }
        }

        binding.textView.setOnClickListener {
            findNavController().navigate(R.id.action_userloginFragment_to_signupFragment)
        }

    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).toolbarTitle.text = "User Login"
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }


}