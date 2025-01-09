package com.example.adminpannel

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminpannel.Adapter.DeliveryAdapter
import com.example.adminpannel.databinding.ActivityOutForBinding
import com.example.adminpannel.model.OrderDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OutForActivity : AppCompatActivity() {
    private val binding:ActivityOutForBinding by lazy {
        ActivityOutForBinding.inflate(layoutInflater)
    }
    private lateinit var database:FirebaseDatabase
    private var listOfCompleteOrderList:ArrayList<OrderDetails> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.back.setOnClickListener {
            finish()
        }
        //retrive and display completed order
        retrieveCompletedOrderDetail()
    }

    private fun retrieveCompletedOrderDetail() {
        //initialize firebase
        database=FirebaseDatabase.getInstance()
        val completedOrderReference=database.reference.child("CompletedOrder")
            .orderByChild("currentTime")
        completedOrderReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear the list before adding new data
                listOfCompleteOrderList.clear()
                for (orderSnapshot in snapshot.children){
                    val completeOrder=orderSnapshot.getValue(OrderDetails::class.java)
                    completeOrder?.let {
                        listOfCompleteOrderList.add(it)
                    }

                }
                //reverse the list to show latest order first
                listOfCompleteOrderList.reverse()
                setDataIntoRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    private fun setDataIntoRecyclerView() {
        //initialization list to show customer name and payment status
        val customerName= mutableListOf<String>()
        val moneyStatus= mutableListOf<Boolean>()
        for (order in listOfCompleteOrderList){
            order.userName?.let {
                customerName.add(it)
            }
            moneyStatus.add(order.paymentRecieved)
        }
        val adapter=DeliveryAdapter(customerName,moneyStatus)
        binding.outfordeliveryRecyclerView.adapter=adapter
        binding.outfordeliveryRecyclerView.layoutManager=LinearLayoutManager(this)
    }
    }
