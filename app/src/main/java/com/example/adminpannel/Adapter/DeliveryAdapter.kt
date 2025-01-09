package com.example.adminpannel.Adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminpannel.databinding.DeliveryitemBinding
import com.example.adminpannel.databinding.ItemItemBinding

class DeliveryAdapter(private val customerName:MutableList<String>,private val moneyStatus:MutableList<Boolean>):RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val binding=DeliveryitemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DeliveryViewHolder(binding)
    }



    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int =customerName.size
    inner class DeliveryViewHolder(private val binding: DeliveryitemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                customer.text=customerName[position]
                if (moneyStatus [position]==true){
                    payment.text="Recieved"
                }
                else{
                    payment.text="NotRecieved"
                }
                //payment.text=moneyStatus[position]
                val colorMap= mapOf(
                    true to Color.GREEN, false to Color.RED
                )
                payment.setTextColor(colorMap[moneyStatus[position]]?:Color.BLACK)
                status.backgroundTintList= ColorStateList.valueOf(colorMap[moneyStatus[position]]?:Color.BLACK)
            }
        }

    }
}