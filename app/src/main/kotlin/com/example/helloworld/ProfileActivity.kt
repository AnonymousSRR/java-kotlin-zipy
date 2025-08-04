package com.example.helloworld

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import android.view.View
import android.widget.ImageView

class ProfileActivity : BaseActivity() {
    
    private lateinit var profileNameTextView: TextView
    private lateinit var profileEmailTextView: TextView
    private lateinit var fullNameValue: TextView
    private lateinit var emailValue: TextView
    private lateinit var backButton: MaterialButton
    private lateinit var editProfileButton: MaterialButton
    private lateinit var changePasswordButton: MaterialButton
    
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_profile
    }
    
    override fun initializeActivity() {
        // Initialize views
        initializeViews()
        
        // Get user data from intent
        val firstName = intent.getStringExtra("FIRST_NAME") ?: ""
        val lastName = intent.getStringExtra("LAST_NAME") ?: ""
        val email = intent.getStringExtra("EMAIL") ?: ""
        
        // Display user information
        val fullName = "$firstName $lastName"
        profileNameTextView.text = fullName
        profileEmailTextView.text = email
        fullNameValue.text = fullName
        emailValue.text = email
        
        // Set up click listeners
        setupClickListeners()
    }
    
    private fun initializeViews() {
        profileNameTextView = findViewById(R.id.profileNameTextView)
        profileEmailTextView = findViewById(R.id.profileEmailTextView)
        fullNameValue = findViewById(R.id.fullNameValue)
        emailValue = findViewById(R.id.emailValue)
        backButton = findViewById(R.id.backButton)
        editProfileButton = findViewById(R.id.editProfileButton)
        changePasswordButton = findViewById(R.id.changePasswordButton)
    }
    
    private fun setupClickListeners() {
        // Back button
        backButton.setOnClickListener {
            finish() // Go back to previous screen
        }
        
        // Edit Profile button
        editProfileButton.setOnClickListener {
            Toast.makeText(this, "Edit Profile feature coming soon!", Toast.LENGTH_SHORT).show()
        }
        
        // Change Password button
        changePasswordButton.setOnClickListener {
            Toast.makeText(this, "Change Password feature coming soon!", Toast.LENGTH_SHORT).show()
        }
    }
} 