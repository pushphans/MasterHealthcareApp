package com.example.masterapp.fragments

import android.content.Intent
import android.net.Uri
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
import com.example.masterapp.databinding.FragmentCareerBinding


class CareerFragment : Fragment(R.layout.fragment_career) {

    private var _binding: FragmentCareerBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCareerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            findNavController().popBackStack(R.id.homeFragment, false)
        }

        binding.btnApply.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val phone = binding.etPhone.text.toString().trim()
            val location = binding.etLocation.text.toString().trim()
            val message = binding.etMessage.text.toString().trim()

            val subject = """
                Career form submission $name 
            """.trimIndent()

            val body = """
                Name : $name
                Email : $email
                Phone : $phone
                Location : $location
                Message : $message
            """.trimIndent()

            if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || location.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please fill in all the required details",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:master.healthcare@yahoo.com" + "?subject=" + Uri.encode(subject) + "&body=" + Uri.encode(body))
                }
                try {
                    binding.etName.text.clear()
                    binding.etPhone.text.clear()
                    binding.etEmail.text.clear()
                    binding.etLocation.text.clear()
                    binding.etMessage.text.clear()
                    startActivity(intent)

                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }

    }


    override fun onResume() {
        super.onResume()

        (activity as MainActivity).toolbarTitle.text = "Career"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}