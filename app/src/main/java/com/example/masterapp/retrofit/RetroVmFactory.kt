package com.example.masterapp.retrofit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RetroVmFactory(private val retroRepository: RetroRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RetroViewModel(retroRepository) as T
    }
}