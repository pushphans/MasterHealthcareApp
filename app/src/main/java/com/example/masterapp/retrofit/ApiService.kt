package com.example.masterapp.retrofit

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("ReadyStockList")
    suspend fun getChemicals() : Response<List<Chemicals>>

    @GET("masterJson")
    suspend fun getAllProducts() : Response<List<AllProducts>>
}