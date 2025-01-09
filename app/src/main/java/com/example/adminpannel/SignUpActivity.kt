package com.example.adminpannel

import android.accounts.Account
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminpannel.databinding.ActivitySignUpBinding
import com.example.adminpannel.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class SignUpActivity : AppCompatActivity() {
    private lateinit var userName:String
    private lateinit var nameOfRestaurant:String
    private lateinit var email: String
    private lateinit var password :String
    private lateinit var auth :FirebaseAuth
    private lateinit var database:DatabaseReference
    private val binding:ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        //initilazation FireBase Auth
        auth = Firebase.auth
        //initialize Firebase database
        database=Firebase.database.reference


        binding.createUserAccount.setOnClickListener{

            //get text form edit text
            userName=binding.nameOfCustomer.text.toString().trim()
            nameOfRestaurant=binding.nameOfRestaurant.text.toString().trim()
            email=binding.emailOrPhone.text.toString().trim()
            password=binding.password.text.toString().trim()
            if (userName.isBlank()||nameOfRestaurant.isBlank()||email.isBlank()||password.isBlank()){
                Toast.makeText(this,"Please Fill All Detail",Toast.LENGTH_SHORT).show()

            }else{
                createAccount(email,password)
            }

        }
        binding.alreadyhaveAccount.setOnClickListener{
            val intent = Intent (this,LoginActivity::class.java)
            startActivity(intent)
        }
        val locationlist = arrayOf("kathamandu","lahan","Bhaktapur")
        val adapter= ArrayAdapter(this,android.R.layout.simple_list_item_1,locationlist)
        val autoCompleteTextView=binding.listoflocation
        autoCompleteTextView.setAdapter(adapter)
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                Toast.makeText(this,"Account Created Successfully",Toast.LENGTH_SHORT).show()
                saveUserData()
                val intent = Intent (this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this,"Account Creation Failed",Toast.LENGTH_SHORT).show()
                Log.d("Account","createAccount:Failure",task.exception)
            }
        }
    }
// save data into database
    private fun saveUserData() {
        //get text form edit text
        userName=binding.nameOfCustomer.text.toString().trim()
        nameOfRestaurant=binding.nameOfRestaurant.text.toString().trim()
        email=binding.emailOrPhone.text.toString().trim()
        password=binding.password.text.toString().trim()
        val user=UserModel(userName,nameOfRestaurant,email,password)
        val userId:String=FirebaseAuth.getInstance().currentUser!!.uid
    //save user data into firebase database
        database.child("user").child(userId).setValue(user)

    }
}