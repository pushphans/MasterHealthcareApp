package com.example.masterapp.retrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.masterapp.R
import com.example.masterapp.StockManager
import com.google.android.material.materialswitch.MaterialSwitch

class AdminStockAdapter(private val onStockChanged : (Chemicals, Boolean) -> Unit) : androidx.recyclerview.widget.ListAdapter<Chemicals, AdminStockAdapter.MyViewHolder>(DiffCallback()) {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val product = view.findViewById<TextView>(R.id.productName)
        val casNo = view.findViewById<TextView>(R.id.casNo)
        val stockSwitch = view.findViewById<MaterialSwitch>(R.id.switchStock)
        val making = view.findViewById<TextView>(R.id.tvMaking)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_admin_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.product.text = item.ProductName
        holder.casNo.text = item.CasNo
        holder.making.text = item.making

        // Current stock status
        val isInStock = !StockManager.isOutOfStock(item.ProductName, item.CasNo)

        holder.stockSwitch.setOnCheckedChangeListener(null)
        holder.stockSwitch.isChecked = isInStock

        holder.stockSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                StockManager.markInStock(item.ProductName)
                StockManager.markInStock(item.CasNo)
            } else {
                StockManager.markOutOfStock(item.ProductName)
                StockManager.markOutOfStock(item.CasNo)
            }
            onStockChanged(item, isChecked)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Chemicals>() {
        override fun areItemsTheSame(oldItem: Chemicals, newItem: Chemicals) =
            oldItem.CasNo == newItem.CasNo

        override fun areContentsTheSame(oldItem: Chemicals, newItem: Chemicals) =
            oldItem == newItem
    }
}
