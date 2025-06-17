package com.example.masterapp.retrofit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RetroViewModel(private val retroRepository: RetroRepository) : ViewModel() {

    private val _chemicalList = MutableStateFlow<List<Chemicals>>(emptyList())
    val chemicalList: StateFlow<List<Chemicals>> get() = _chemicalList
    private val _allProductList = MutableStateFlow<List<AllProducts>>(emptyList())
    val allProductsList : StateFlow<List<AllProducts>> get() = _allProductList


    fun getChemicals() {
        viewModelScope.launch {
            val response = retroRepository.getChemicals().body()

            try {
                if (response != null) {
                    Log.d("myTag", "view Model $response")
                    _chemicalList.value = response
                } else {
                    Log.d("myTag", "ViewModel Error is null response")
                }
            } catch (e: HttpException) {
                Log.d("myTag", "ViewModel Error is ${e.message}")
            }

        }
    }

    fun allProducts(){
        viewModelScope.launch {
            val response = retroRepository.allChemicals().body()

            try{
                if(response != null){
                    Log.d("ResponseAllProducts", "view Model $response")
                    _allProductList.value = response
                }else{
                    Log.d("myTag", "Error")
                }
            }catch (e : HttpException){
                Log.d("myTag", "allProductFunction() Error is ${e.message}")
            }
        }
    }

}