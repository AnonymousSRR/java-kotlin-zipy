package com.example.helloworld

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton

class ApiKeyActivity : BaseActivity() {
    
    private lateinit var apiKeyEditText: EditText
    private lateinit var nextButton: MaterialButton
    
    companion object {
        // Static variable to store API key globally
        var storedApiKey: String = ""
    }
    
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_api_key
    }
    
    override fun initializeActivity() {
        // Initialize views
        initializeViews()
        
        // Set up click listener
        nextButton.setOnClickListener {
            handleNextButton()
        }
    }
    
    private fun initializeViews() {
        apiKeyEditText = findViewById(R.id.apiKeyEditText)
        nextButton = findViewById(R.id.nextButton)
    }
    
    private fun handleNextButton() {
        val apiKey = apiKeyEditText.text.toString().trim()
        
        if (apiKey.isEmpty()) {
            Toast.makeText(this, "Please enter an API key", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Store the API key in the static variable
        storedApiKey = apiKey
        
        // Show success message
        Toast.makeText(this, "API key stored successfully!", Toast.LENGTH_SHORT).show()
        
        // Navigate to signup page
        val intent = android.content.Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Close the API key activity
    }
} 