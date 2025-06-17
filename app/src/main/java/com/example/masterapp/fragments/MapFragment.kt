package com.example.masterapp.fragments

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.GeolocationPermissions
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.masterapp.MainActivity
import com.example.masterapp.R
import com.example.masterapp.databinding.FragmentMapBinding


class MapFragment : Fragment(R.layout.fragment_map) {

    private var _binding : FragmentMapBinding? = null
    private val binding get() = _binding!!


    private val fineLocation = android.Manifest.permission.ACCESS_FINE_LOCATION
    private val coarseLocation = android.Manifest.permission.ACCESS_COARSE_LOCATION
    private val permission = mutableListOf(fineLocation, coarseLocation)
    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){permission ->
        val granted = permission.all{
            it.value == true
        }
        if(granted == true){
            if(isLocationEnabled()){
                setUpWebView()
                initializeWebView()
            }
            else{
                Toast.makeText(requireContext(), "Turn on location of device from the settings", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(requireContext(), "Some permissions are missing", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView() {
        binding.mapWebView.settings.javaScriptEnabled = true
        binding.mapWebView.settings.setGeolocationEnabled(true)
        binding.mapWebView.webChromeClient = object : WebChromeClient(){
            override fun onGeolocationPermissionsShowPrompt(
                origin: String?,
                callback: GeolocationPermissions.Callback?
            ) {
                callback?.invoke(origin, true, false)
            }
        }
        binding.mapWebView.webViewClient = WebViewClient()
    }

    private fun initializeWebView(){
        binding.mapWebView.loadUrl("https://maps.google.com/?q=30.9337046,76.7998758")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        permissionCheck()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            findNavController().popBackStack(R.id.contactusFragment, false)
        }

    }

    private fun permissionCheck(){
        val missingPermissions = permission.filter {
            ContextCompat.checkSelfPermission(requireContext(), it) != PackageManager.PERMISSION_GRANTED
        }
        if(missingPermissions.isNotEmpty()){
            permissionLauncher.launch(missingPermissions.toTypedArray())
        } else {
            if(isLocationEnabled()){
                setUpWebView()
                initializeWebView()
            } else {
                Toast.makeText(requireContext(), "Turn on location from the settings", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun isLocationEnabled() : Boolean{
        val locationManager = requireContext().getSystemService(LocationManager::class.java)
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }


    override fun onResume() {
        super.onResume()

        (activity as MainActivity).toolbarTitle.text = "Maps"
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}