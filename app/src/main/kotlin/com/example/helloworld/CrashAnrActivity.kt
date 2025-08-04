package com.example.helloworld

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import android.os.Handler
import android.os.Looper

class CrashAnrActivity : BaseActivity() {
    
    private lateinit var backButton: MaterialButton
    private lateinit var crashButton: MaterialButton
    private lateinit var anrButton: MaterialButton
    private lateinit var statusTextView: TextView
    
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_crash_anr
    }
    
    override fun initializeActivity() {
        initializeViews()
        setupClickListeners()
    }
    
    private fun initializeViews() {
        backButton = findViewById(R.id.backButton)
        crashButton = findViewById(R.id.crashButton)
        anrButton = findViewById(R.id.anrButton)
        statusTextView = findViewById(R.id.statusTextView)
    }
    
    private fun setupClickListeners() {
        backButton.setOnClickListener {
            finish()
        }
        
        crashButton.setOnClickListener {
            performCrash()
        }
        
        anrButton.setOnClickListener {
            performANR()
        }
    }
    
    private fun performCrash() {
        statusTextView.text = "💥 Preparing to crash the app..."
        Toast.makeText(this, "Crashing in 2 seconds...", Toast.LENGTH_SHORT).show()
        
        // Delay the crash by 2 seconds to show the status
        Handler(Looper.getMainLooper()).postDelayed({
            statusTextView.text = "💥 CRASHING NOW!"
            
            // Multiple crash methods to ensure it crashes
            try {
                // Method 1: Null pointer exception
                val nullString: String? = null
                nullString!!.length
            } catch (e: Exception) {
                // Method 2: Array index out of bounds
                val arr = arrayOf(1, 2, 3)
                val value = arr[10] // This will crash
            }
            
            // Method 3: Division by zero
            val result = 1 / 0
            
            // Method 4: If somehow we reach here, throw a RuntimeException
            throw RuntimeException("Intentional app crash triggered by user!")
        }, 2000)
    }
    
    private fun performANR() {
        statusTextView.text = "⏰ Starting ANR (Application Not Responding)..."
        Toast.makeText(this, "ANR will occur in 2 seconds...", Toast.LENGTH_SHORT).show()
        
        // Delay the ANR by 2 seconds to show the status
        Handler(Looper.getMainLooper()).postDelayed({
            statusTextView.text = "⏰ BLOCKING MAIN THREAD - ANR IN PROGRESS..."
            
            // Block the main thread to cause ANR
            try {
                // Force ANR by sleeping on main thread - this will definitely trigger ANR dialog
                statusTextView.text = "⏰ Sleeping on main thread for 15 seconds..."
                
                // This will definitely cause ANR on any Android device
                Thread.sleep(15000) // Sleep for 15 seconds on main thread
                
                // After the ANR period, resume normally
                statusTextView.text = "✅ ANR period completed! App resumed normally."
                Toast.makeText(this, "ANR completed - app resumed!", Toast.LENGTH_SHORT).show()
                
            } catch (e: Exception) {
                statusTextView.text = "❌ ANR interrupted: ${e.message}"
            }
        }, 2000)
    }
} 