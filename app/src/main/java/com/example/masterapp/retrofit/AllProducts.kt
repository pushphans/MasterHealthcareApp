package com.example.masterapp.retrofit

import com.google.gson.annotations.SerializedName

data class AllProducts(
    @SerializedName("CasNo")
    val casNo : String,

    @SerializedName("ProductName")
    val productName : String
)
