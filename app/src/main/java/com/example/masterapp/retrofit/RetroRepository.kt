package com.example.masterapp.retrofit

import android.util.Log
import retrofit2.Response

class RetroRepository(private val apiService: ApiService){

    suspend fun getChemicals() : Response<List<Chemicals>>{
        val response = apiService.getChemicals()
        return response
    }

    suspend fun allChemicals() : Response<List<AllProducts>>{
        val response = apiService.getAllProducts()
        return response
    }

}