package com.example.masterapp

import android.content.Context
import android.content.SharedPreferences

object StockManager {
    private const val key = "outOfStock"

    private lateinit var prefs: SharedPreferences
    fun init(context: Context) {
        prefs = context.getSharedPreferences("stock_prefs", Context.MODE_PRIVATE)
    }

    fun setOutOfStockList(list: List<String>) {
        val csv = list.joinToString(",")
        prefs.edit().putString(key, csv).apply()
    }

    fun getOutOfStockList(): List<String> {
        val csv = prefs.getString(key, "") ?: ""
        return if (csv.isEmpty()) {
            emptyList()
        } else {
            csv.split(",")
        }
    }

    fun isOutOfStock(productName: String, casNo : String): Boolean {
        val list = getOutOfStockList()
        return productName in list || casNo in list
    }

    fun markOutOfStock(key: String) {
        val current = getOutOfStockList().toMutableSet()
        current.add(key)
        setOutOfStockList(current.toList())
    }

    fun markInStock(key: String) {
        val current = getOutOfStockList().toMutableSet()
        current.remove(key)
        setOutOfStockList(current.toList())
    }
}