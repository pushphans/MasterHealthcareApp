package com.example.masterapp.retrofit

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.masterapp.R
import com.example.masterapp.StockManager

class RetroAdapter : androidx.recyclerview.widget.ListAdapter<Chemicals, RetroAdapter.MyViewHolder>(DiffCallback()){




    inner class MyViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val casNo = view.findViewById<TextView>(R.id.textView2)
        val product = view.findViewById<TextView>(R.id.textView)
        val stockStatus = view.findViewById<TextView>(R.id.tvStock)
        val making = view.findViewById<TextView>(R.id.tvMaking)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.item_layout , parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.casNo.text = item.CasNo
        holder.product.text = item.ProductName
        holder.making.text = "Making : ${item.making}"

        val isOutOfStock = StockManager.isOutOfStock(item.ProductName, item.CasNo)

        holder.stockStatus.text = if(isOutOfStock == true){
            "Out of Stock"
        }
        else{
            "In Stock"
        }

        holder.stockStatus.setTextColor(
            holder.itemView.context.getColor(
                if(isOutOfStock == true){R.color.red}
                else {R.color.green}
            )
        )

        Log.d("myTag", "Product: ${item.ProductName}, CAS: ${item.CasNo}")

    }

    class DiffCallback() : DiffUtil.ItemCallback<Chemicals>(){
        override fun areItemsTheSame(oldItem: Chemicals, newItem: Chemicals): Boolean {
            return oldItem.ProductName == newItem.ProductName && oldItem.making == newItem.making
        }

        override fun areContentsTheSame(oldItem: Chemicals, newItem: Chemicals): Boolean {
            return oldItem == newItem
        }
    }

}