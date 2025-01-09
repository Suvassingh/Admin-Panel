package com.example.adminpannel

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminpannel.databinding.ActivityNewUserAdminBinding

class NewUserAdminActivity : AppCompatActivity() {
    private val binding:ActivityNewUserAdminBinding by lazy {
        ActivityNewUserAdminBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.backfromnewuseradmin.setOnClickListener {
            finish()
        }

    }
}