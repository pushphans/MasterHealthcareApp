package com.example.masterapp.retrofit

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Chemicals(
    @SerializedName("CasNo")
    val CasNo: String,

    @SerializedName("ProductName")
    val ProductName: String,

    @SerializedName("Unit")
    val unit : String,

    @SerializedName("Making")
    val making : String,


    val quantity : Int = 0

) : Parcelable