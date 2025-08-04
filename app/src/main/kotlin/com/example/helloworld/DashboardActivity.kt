package com.example.helloworld

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton

class DashboardActivity : BaseActivity() {
    
    private lateinit var userDetailsTextView: TextView
    private lateinit var logoutButton: MaterialButton
    
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_dashboard
    }
    
    override fun initializeActivity() {
        // Initialize views
        userDetailsTextView = findViewById(R.id.userDetailsTextView)
        logoutButton = findViewById(R.id.logoutButton)
        
        // Get user data from intent
        val firstName = intent.getStringExtra("FIRST_NAME") ?: ""
        val lastName = intent.getStringExtra("LAST_NAME") ?: ""
        val email = intent.getStringExtra("EMAIL") ?: ""
        
        // Display user information
        val fullName = "$firstName $lastName"
        userDetailsTextView.text = "Name: $fullName\nEmail: $email"
        
        // Set up all button click listeners
        setupButtonListeners()
    }
    
    private fun setupButtonListeners() {
        // Logout button
        logoutButton.setOnClickListener {
            Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show()
            
            // Navigate back to signup page instead of closing the app
            val intent = android.content.Intent(this, MainActivity::class.java)
            intent.flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK or android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
        
        // My Profile button
        findViewById<MaterialButton>(R.id.myProfileButton).setOnClickListener {
            // Navigate to Profile Activity with user data
            val intent = android.content.Intent(this, ProfileActivity::class.java).apply {
                putExtra("FIRST_NAME", intent.getStringExtra("FIRST_NAME") ?: "")
                putExtra("LAST_NAME", intent.getStringExtra("LAST_NAME") ?: "")
                putExtra("EMAIL", intent.getStringExtra("EMAIL") ?: "")
            }
            startActivity(intent)
        }
        
        // Hit APIs button - Navigate to Hit APIs screen
        findViewById<MaterialButton>(R.id.hitApisButton).setOnClickListener {
            val intent = android.content.Intent(this, HitApisActivity::class.java)
            startActivity(intent)
        }
        
        // Generate Errors button
        findViewById<MaterialButton>(R.id.generateErrorsButton).setOnClickListener {
            val intent = android.content.Intent(this, GenerateErrorsActivity::class.java)
            startActivity(intent)
        }
        
        // ANR/CRASH button
        findViewById<MaterialButton>(R.id.anrCrashButton).setOnClickListener {
            val intent = android.content.Intent(this, CrashAnrActivity::class.java)
            startActivity(intent)
        }
        
        // Scroll button
        findViewById<MaterialButton>(R.id.scrollButton).setOnClickListener {
            val intent = android.content.Intent(this, ScrollActivity::class.java)
            startActivity(intent)
        }
        
        // Drag and Drop button
        findViewById<MaterialButton>(R.id.dragDropButton).setOnClickListener {
            val intent = android.content.Intent(this, DragDropActivity::class.java)
            startActivity(intent)
        }
        
        // Animations button
        findViewById<MaterialButton>(R.id.animationsButton).setOnClickListener {
            val intent = android.content.Intent(this, AnimationsActivity::class.java)
            startActivity(intent)
        }
        
        // Settings button
        findViewById<MaterialButton>(R.id.settingsButton).setOnClickListener {
            val intent = android.content.Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        
        // Controls button
        findViewById<MaterialButton>(R.id.controlsButton).setOnClickListener {
            val intent = android.content.Intent(this, ControlsActivity::class.java)
            startActivity(intent)
        }
        
        // Pay button
        findViewById<MaterialButton>(R.id.payButton).setOnClickListener {
            val intent = android.content.Intent(this, PayActivity::class.java)
            startActivity(intent)
        }
        
        // App Hide button
        findViewById<MaterialButton>(R.id.appHideButton).setOnClickListener {
            hideAppFor5Seconds()
        }
        
        // Mini Game button
        findViewById<MaterialButton>(R.id.miniGameButton).setOnClickListener {
            val intent = android.content.Intent(this, MiniGameActivity::class.java)
            startActivity(intent)
        }
    }
    
    private fun hideAppFor5Seconds() {
        // Show a toast message
        Toast.makeText(this, "App will hide for 5 seconds...", Toast.LENGTH_SHORT).show()
        
        // Minimize the app to background
        moveTaskToBack(true)
        
        // Bring app back to foreground after 5 seconds
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            // Bring the app back to foreground
            val intent = android.content.Intent(this, DashboardActivity::class.java)
            intent.addFlags(android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
            
            // Show a toast message that app is back
            Toast.makeText(this, "App is back in foreground!", Toast.LENGTH_SHORT).show()
        }, 5000) // 5 seconds delay
    }
} 