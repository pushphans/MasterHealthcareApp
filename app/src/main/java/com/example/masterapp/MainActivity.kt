package com.example.masterapp

import android.os.Bundle
import android.widget.TextView
import android.widget.Toolbar
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.masterapp.databinding.ActivityMainBinding
import com.example.masterapp.fragments.HomeFragment
import com.example.masterapp.repository.AuthRepository
import com.example.masterapp.retrofit.ApiService
import com.example.masterapp.retrofit.RetroRepository
import com.example.masterapp.retrofit.RetroViewModel
import com.example.masterapp.retrofit.RetroVmFactory
import com.example.masterapp.retrofit.RetrofitInstance
import com.example.masterapp.viewmodel.AuthViewModel
import com.example.masterapp.viewmodel.VMFactory

class MainActivity : AppCompatActivity() {
    private lateinit var  binding : ActivityMainBinding

    private lateinit var viewmodel : AuthViewModel
    private lateinit var retroViewmodel : RetroViewModel

    lateinit var toolbarTitle : TextView
    lateinit var toolbar : androidx.appcompat.widget.Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.cardColor3)

        StockManager.init(applicationContext)


        val repository = AuthRepository()
        viewmodel = ViewModelProvider(this, VMFactory(repository))[AuthViewModel::class.java]


        val apiService = RetrofitInstance.apiService
        val retroRepository = RetroRepository(apiService)
        retroViewmodel = ViewModelProvider(this, RetroVmFactory(retroRepository))[RetroViewModel::class.java]


        toolbar = binding.toolbar

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        toolbarTitle = binding.toolbarTitle

        binding.drawer.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.homePage ->{
                    findNavController(R.id.fragmentContainerView).navigate(R.id.homeFragment)
                }

                R.id.ourStory ->{
                    findNavController(R.id.fragmentContainerView).navigate(R.id.aboutusFragment)
                }

                R.id.masterhealthcare ->{
                    findNavController(R.id.fragmentContainerView).navigate(R.id.masterhealthcareFragment)
                }

                R.id.masterlifecareproducts ->{
                    findNavController(R.id.fragmentContainerView).navigate(R.id.homeFragment)
                }

                R.id.masterpacking ->{
                    findNavController(R.id.fragmentContainerView).navigate(R.id.masterpackingFragment)
                }

                R.id.masterbuildcon ->{
                    findNavController(R.id.fragmentContainerView).navigate(R.id.homeFragment)
                }

                R.id.nerimasterantibiotics ->{
                    findNavController(R.id.fragmentContainerView).navigate(R.id.homeFragment)
                }

                R.id.nerimasterpharma ->{
                    findNavController(R.id.fragmentContainerView).navigate(R.id.homeFragment)
                }

                R.id.masterservices ->{
                    findNavController(R.id.fragmentContainerView).navigate(R.id.homeFragment)
                }

                R.id.manufacturing ->{
                    findNavController(R.id.fragmentContainerView).navigate(R.id.contractmanufacturingFragment)
                }

                R.id.quality ->{
                    findNavController(R.id.fragmentContainerView).navigate(R.id.qualityFragment)

                }

                R.id.career ->{
                    findNavController(R.id.fragmentContainerView).navigate(R.id.careerFragment)
                }

                R.id.media ->{
                    findNavController(R.id.fragmentContainerView).navigate(R.id.homeFragment)
                }

                R.id.contactus ->{
                    findNavController(R.id.fragmentContainerView).navigate(R.id.contactusFragment)
                }
            }
            binding.drawerLayout.closeDrawers()
            true
        }



        onBackPressedDispatcher.addCallback(this){
            if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }else{
                finish()
            }
        }


    }
}