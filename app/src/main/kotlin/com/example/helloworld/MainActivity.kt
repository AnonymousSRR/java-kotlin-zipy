package com.example.helloworld

import android.os.Bundle
import android.widget.Toast
import android.widget.EditText
import com.example.helloworld.java.FormValidator
import com.google.android.material.button.MaterialButton
import android.content.Intent

class MainActivity : BaseActivity() {
    
    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var signUpButton: MaterialButton
    
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_main
    }
    
    override fun initializeActivity() {
        // Initialize views
        initializeViews()
        
        // Set up signup button click listener
        signUpButton.setOnClickListener {
            if (validateForm()) {
                // Navigate to Dashboard with user data
                val intent = Intent(this, DashboardActivity::class.java).apply {
                    putExtra("FIRST_NAME", firstNameEditText.text.toString())
                    putExtra("LAST_NAME", lastNameEditText.text.toString())
                    putExtra("EMAIL", emailEditText.text.toString())
                }
                startActivity(intent)
            }
        }
        
        // Set up Change API Key button click listener
        findViewById<MaterialButton>(R.id.changeApiKeyButton).setOnClickListener {
            val intent = Intent(this, ApiKeyActivity::class.java)
            startActivity(intent)
            finish() // Close the current activity
        }
    }
    
    private fun initializeViews() {
        firstNameEditText = findViewById(R.id.firstNameEditText)
        lastNameEditText = findViewById(R.id.lastNameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)
        signUpButton = findViewById(R.id.signUpButton)
    }
    
    private fun validateForm(): Boolean {
        val firstName = firstNameEditText.text.toString().trim()
        val lastName = lastNameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString()
        val confirmPassword = confirmPasswordEditText.text.toString()
        
        // Validate form using Java helper class
        val validationResult = FormValidator.validateSignupForm(
            firstName, lastName, email, password, confirmPassword
        )
        
        if (validationResult == "SUCCESS") {
            return true
        } else {
            Toast.makeText(this, validationResult, Toast.LENGTH_LONG).show()
            return false
        }
    }
} 