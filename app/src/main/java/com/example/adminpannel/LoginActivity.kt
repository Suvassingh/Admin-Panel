package com.example.adminpannel

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge

import androidx.appcompat.app.AppCompatActivity

import com.example.adminpannel.databinding.ActivityLoginBinding
import com.example.adminpannel.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class LoginActivity : AppCompatActivity() {
    private var userName:String?=null
    private var nameOfRestaurant:String?=null
    private lateinit var email:String
    private lateinit var password:String
    private lateinit var auth: FirebaseAuth
    private lateinit var database:DatabaseReference


    private val binding:ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        
        //initilizing firebase auth
        auth=Firebase.auth
        //initilize firebase database
        database=Firebase.database.reference


        binding.loginbotton.setOnClickListener{
            //getting data from edit text
            email=binding.emailOrPhone.text.toString().trim()
            password=binding.password.text.toString().trim()
            if (email.isBlank()||password.isBlank()){
                Toast.makeText(this,"Please Fill All Detail",Toast.LENGTH_SHORT).show()
            }else{
                cerateUserAccount(email,password)
            }
        }

        binding.donthaveAccount.setOnClickListener{
            val intent = Intent (this,SignUpActivity::class.java)
            startActivity(intent)
        }

    }

    private fun cerateUserAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val user=auth.currentUser
                Toast.makeText(this,"Login Successfully",Toast.LENGTH_SHORT).show()
                updateUi(user)
            }else{
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        val user=auth.currentUser
                        Toast.makeText(this," Create User And Login Successfully",Toast.LENGTH_SHORT).show()

                        saveUserData()
                        updateUi(user)
                    }else{
                        Toast.makeText(this,"Authencation Failed",Toast.LENGTH_SHORT).show()
                        Log.d("Account","createUserAccount:Authencation Failed",task.exception)
                    }
                }
            }


        }
    }

    private fun saveUserData() {
        //get data from edit text
        email=binding.emailOrPhone.text.toString().trim()
        password=binding.password.text.toString().trim()
        val user=UserModel(userName,nameOfRestaurant,email,password)
        val userId=FirebaseAuth.getInstance().currentUser?.uid
        userId?.let {
            database.child("user").child(it).setValue(user)
        }

    }

    private fun updateUi(user: FirebaseUser?) {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

}