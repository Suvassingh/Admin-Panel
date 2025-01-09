package com.example.adminpannel

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminpannel.databinding.ActivityMainBinding
import com.example.adminpannel.model.OrderDetails
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var completedOrderReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth=Firebase.auth
        binding.addMenu.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            startActivity(intent)
        }
        binding.allItem.setOnClickListener {
            val intent = Intent(this, AllItemActivity::class.java)
            startActivity(intent)
        }
        binding.dispatch.setOnClickListener {
            val intent = Intent(this, OutForActivity::class.java)
            startActivity(intent)
        }
        binding.adminProfile.setOnClickListener {
            val intent = Intent(this, AdminProfileActivity::class.java)
            startActivity(intent)
        }
        binding.newUser.setOnClickListener {
            val intent = Intent(this, NewUserAdminActivity::class.java)
            startActivity(intent)
        }
        binding.PendingOrder.setOnClickListener {
            val intent = Intent(this, PendingOrderActivity::class.java)
            startActivity(intent)
        }
        binding.logout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this,SignUpActivity::class.java))
            finish()
        }

        pendingOrders()
        completedOrders()
        wholeTimeEarning()
    }

    private fun wholeTimeEarning() {
        var listOfTotalPay= mutableListOf<Int>()
        completedOrderReference=FirebaseDatabase.getInstance().reference.child("CompletedOrder")
        completedOrderReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (orderSnapshot in snapshot.children){
                    var completeOrder=orderSnapshot.getValue(OrderDetails::class.java)
                    completeOrder?.totalPrice?.replace("$","")?.toIntOrNull()
                        ?.let { i->
                            listOfTotalPay.add(i)
                        }
                }
                binding.wholeTimeEarning.text=listOfTotalPay.sum().toString()+"$"
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

    private fun completedOrders() {
        val completedOrderReference = database.reference.child("CompletedOrder")
        var completedOrderItemCount = 0
        completedOrderReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                completedOrderItemCount = snapshot.childrenCount.toInt()
                binding.completeOrders.text = completedOrderItemCount.toString()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

    private fun pendingOrders() {
        database = FirebaseDatabase.getInstance()
        val pendingOrderReference = database.reference.child("OrderDetails")
        var pendingOrderItemCount = 0
        pendingOrderReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                pendingOrderItemCount = snapshot.childrenCount.toInt()
                binding.pendingOrders.text = pendingOrderItemCount.toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


    }
}