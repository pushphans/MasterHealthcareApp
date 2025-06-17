package com.example.masterapp.retrofit

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListDataClass(
    val selectedItem : List<Chemicals>

): Parcelable
