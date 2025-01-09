package com.example.adminpannel

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminpannel.Adapter.OrderDetailsAdapter
import com.example.adminpannel.databinding.ActivityOrderDetailsBinding
import com.example.adminpannel.model.OrderDetails

class OrderDetailsActivity : AppCompatActivity() {
    private val binding:ActivityOrderDetailsBinding by lazy {
        ActivityOrderDetailsBinding.inflate(layoutInflater)
    }
    private var userName:String?=null
    private var address:String?=null
    private var phoneNumber:String?=null
    private var totalPrice:String?=null
    private  var foodNames:ArrayList<String> = arrayListOf()
    private var foodImages:ArrayList<String> = arrayListOf()
    private  var foodQuantity:ArrayList<Int> = arrayListOf()
    private var foodPrices:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.backBotton.setOnClickListener {
            finish()
        }
        getDataFromIntent()

    }

    private fun getDataFromIntent() {
        val receivedOrderDetails=intent.getSerializableExtra("UserOrderDetails") as OrderDetails
        receivedOrderDetails?.let { receivedOrderDetails->

            userName=receivedOrderDetails.userName
            foodNames=receivedOrderDetails.foodNames as ArrayList<String>
            foodImages=receivedOrderDetails.foodImages as ArrayList<String>
            foodQuantity=receivedOrderDetails.foodQuantities as ArrayList<Int>
            address=receivedOrderDetails.address
            phoneNumber=receivedOrderDetails.phoneNumber
            foodPrices=receivedOrderDetails.foodprices
            totalPrice=receivedOrderDetails.totalPrice
            setUserDetails()
            setAdapter()
        }
    }

    private fun setAdapter() {
        binding.orderdetailrecyclerview.layoutManager=LinearLayoutManager(this)
        val adapter=OrderDetailsAdapter(this,foodNames,foodImages,foodQuantity,foodPrices)
        binding.orderdetailrecyclerview.adapter=adapter
    }

    private fun setUserDetails() {
        binding.name.text=userName
        binding.address.text=address
        binding.phone.text=phoneNumber
        binding.totalpayment.text=totalPrice
    }
}