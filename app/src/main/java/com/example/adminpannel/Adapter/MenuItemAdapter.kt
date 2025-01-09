package com.example.adminpannel.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminpannel.databinding.ItemItemBinding
import com.example.adminpannel.model.AllMenu
import com.google.firebase.database.DatabaseReference

class MenuItemAdapter(
    private val context: Context,
    private val menuList: ArrayList<AllMenu>,
    databaseReference: DatabaseReference,
    private val onDeleteClickListener:(position:Int) -> Unit

):RecyclerView.Adapter<MenuItemAdapter.AddItemHolder> (){
    private val itemQuantities = IntArray(menuList.size){1}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemHolder {
        val binding=ItemItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddItemHolder(binding)
    }



    override fun onBindViewHolder(holder: AddItemHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int =menuList.size
    inner class AddItemHolder (private val binding: ItemItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            binding.apply {
                val menuItem=menuList[position]
                val uriString=menuItem.foodImage
                val uri= Uri.parse(uriString)
                price.text=menuItem.foodPrice
                name.text=menuItem.foodName
                Glide.with(context).load(uri).into(foodImageView)
                itemQuantities.forEach { quantity}
                minusBotton.setOnClickListener {
                    decreaseQuantity(adapterPosition)

                }
                plusBotton.setOnClickListener {
                    increaseQuantity(adapterPosition)

                }
                deleteBotton.setOnClickListener {
                    onDeleteClickListener(position)

                }


            }

        }
        private fun decreaseQuantity(position: Int) {
            if (itemQuantities[position]>1){
                itemQuantities[position]--
                binding.quantity.text=itemQuantities[position].toString()
            }

        }
        private fun increaseQuantity(position: Int) {
            if (itemQuantities[position]<10){
                itemQuantities[position]++
                binding.quantity.text=itemQuantities[position].toString()
            }

        }
        private fun deleteItem(position: Int) {
            menuList.removeAt(position)
            menuList.removeAt(position)
            menuList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,menuList.size)

        }




    }
}