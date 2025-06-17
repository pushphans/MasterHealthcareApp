package com.example.masterapp.retrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.masterapp.R

class AllProductsAdapter : ListAdapter<AllProducts, AllProductsAdapter.AllViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.all_products_list_user, parent, false)
        return AllViewHolder(view)
    }

    override fun onBindViewHolder(holder: AllViewHolder, position: Int) {
        val item = getItem(position)
        holder.casNo.text = item.casNo
        holder.productName.text = item.productName
    }


    inner class AllViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val casNo = view.findViewById<TextView>(R.id.tvCasNoAllProducts)
        val productName = view.findViewById<TextView>(R.id.tvNameAllProducts)
    }

    class DiffCallback() : DiffUtil.ItemCallback<AllProducts>(){
        override fun areItemsTheSame(oldItem: AllProducts, newItem: AllProducts): Boolean {
            return oldItem.casNo == newItem.casNo && oldItem.productName == newItem.productName
        }

        override fun areContentsTheSame(oldItem: AllProducts, newItem: AllProducts): Boolean {
            return oldItem == newItem
        }
    }

}