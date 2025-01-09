package com.example.adminpannel

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminpannel.databinding.ActivityAdminProfileBinding
import com.example.adminpannel.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminProfileActivity : AppCompatActivity() {
    private val binding:ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var adminReference:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance()
        adminReference=database.reference.child("user")
        binding.backadmin.setOnClickListener {
            finish()
        }
        binding.saveInfo.setOnClickListener {
            updateUserData()
        }
        binding.nameis.isEnabled=false
        binding.addressis.isEnabled=false
        binding.emailis.isEnabled=false
        binding.phoneis.isEnabled=false
        binding.passwordis.isEnabled=false
        binding.saveInfo.isEnabled=false
        var isenable =false
        binding.editbotton.setOnClickListener {
            isenable = ! isenable
            binding.nameis.isEnabled=isenable
            binding.addressis.isEnabled=isenable
            binding.emailis.isEnabled=isenable
            binding.phoneis.isEnabled=isenable
            binding.passwordis.isEnabled=isenable
            if (isenable){
                binding.nameis.requestFocus()
            }
            binding.saveInfo.isEnabled=isenable
        }
        retrieveUserData()

    }



    private fun retrieveUserData() {
        val currentUserUid=auth.currentUser?.uid
        if (currentUserUid != null){
            val userReference=adminReference.child(currentUserUid)
            userReference.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        val ownerName=snapshot.child("name").getValue()
                        val email=snapshot.child("email").getValue()
                        val password=snapshot.child("password").getValue()
                        val address=snapshot.child("address").getValue()
                        val phone=snapshot.child("phone").getValue()
                        setDataToTextView(ownerName,email,password,address,phone)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }

    }

    private fun setDataToTextView(
        ownerName: Any?,
        email: Any?,
        password: Any?,
        address: Any?,
        phone: Any?
    ) {
        binding.nameis.setText(ownerName.toString())
        binding.emailis.setText(email.toString())
        binding.passwordis.setText(password.toString())
        binding.phoneis.setText(phone.toString())
        binding.addressis.setText(address.toString())

    }
    private fun updateUserData() {
        val updateName=binding.nameis.text.toString()
        val updateEmail=binding.emailis.text.toString()
        val updatePassword=binding.passwordis.text.toString()
        val updatePhone=binding.phoneis.text.toString()
        val updateAddress=binding.addressis.text.toString()
        val currentUserUid = auth.currentUser?.uid
        if (currentUserUid !=null){
            val userReference=adminReference.child(currentUserUid)
            userReference.child("name").setValue(updateName)
            userReference.child("email").setValue(updateEmail)
            userReference.child("password").setValue(updatePassword)
            userReference.child("phone").setValue(updatePhone)
            userReference.child("address").setValue(updateAddress)
            Toast.makeText(this, "Profile Updated Successfully...", Toast.LENGTH_SHORT).show()
            //auth the email and password
            auth.currentUser?.updateEmail(updateEmail)
            auth.currentUser?.updatePassword(updatePassword)
        }


        else {
            Toast.makeText(this, "Profile Update Failed...", Toast.LENGTH_SHORT).show()
        }


    }
}