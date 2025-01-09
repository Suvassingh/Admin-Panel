package com.example.adminpannel

import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.adminpannel.databinding.ActivityAddItemBinding
import com.example.adminpannel.model.AllMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddItemActivity : AppCompatActivity() {
    // food item detail
    private lateinit var foodName:String
    private lateinit var foodPrice:String
    private lateinit var foodDescription:String
    private lateinit var foodIngridients:String

    private  var foodImageUri: Uri?=null

    //firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private val binding:ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        //initilazation firebase
        auth=FirebaseAuth.getInstance()
        //initizile firebase database instance
        database=FirebaseDatabase.getInstance()
        binding.addItemButton.setOnClickListener {
            // get data from fileld
            foodName=binding.foodName.text.toString().trim()
            foodPrice=binding.foodPrice.text.toString().trim()
            foodDescription=binding.description.text.toString().trim()
            foodIngridients=binding.ingridients.text.toString().trim()
            if (!(foodName.isBlank()||foodPrice.isBlank()||foodDescription.isBlank()||foodIngridients.isBlank())){
                uploadData()
                Toast.makeText(this,"Item Added Successfully",Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                Toast.makeText(this,"Please fill all the detail",Toast.LENGTH_SHORT).show()
            }
        }
        binding.selectImage.setOnClickListener{
            pickImage.launch("image/*")
        }

        binding.backbotton.setOnClickListener {
            finish()
        }


    }

    private fun uploadData() {
        //get a reference to the "menu" node in the database
        val MenuRef=database.getReference("menu")
        //generate a unique key for the new menu item
        val newItemKey=MenuRef.push().key

        if (foodImageUri!=null){
            val storageRef=FirebaseStorage.getInstance().reference
            val imageRef=storageRef.child("menu_images/${newItemKey}.jpg")
            val uploadTask = imageRef.putFile(foodImageUri!!)
            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener {
                    downloadUrl->
                    //create a new menu item
                    val newItem =AllMenu(
                        newItemKey,
                        foodName=foodName,

                        foodPrice=foodPrice,
                        foodDescription=foodDescription,
                        foodIngridients=foodIngridients,
                        foodImage = downloadUrl.toString()
                    )
                    newItemKey?.let {
                        key->
                        MenuRef.child(key).setValue(newItem).addOnSuccessListener{
                            Toast.makeText(this, "data uploaded successfully", Toast.LENGTH_SHORT).show()
                        }
                            .addOnFailureListener {
                                Toast.makeText(this, "data uploaded failed", Toast.LENGTH_SHORT).show()
                            }
                    }

                }

            }
                .addOnFailureListener {
                    Toast.makeText(this, "Image Uploaded failed", Toast.LENGTH_SHORT).show()

                }

        }else {
            Toast.makeText(this, "please select an image", Toast.LENGTH_SHORT).show()

        }

    }
    private val pickImage=registerForActivityResult(ActivityResultContracts.GetContent()){uri ->
        if (uri != null){
            binding.selectImage.setImageURI(uri)
            foodImageUri=uri
        }
    }
}

private fun Any.setImageURI(uri: Uri) {


}




