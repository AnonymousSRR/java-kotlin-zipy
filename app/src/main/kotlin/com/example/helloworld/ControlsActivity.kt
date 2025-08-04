package com.example.helloworld

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton

class ControlsActivity : BaseActivity() {
    
    private lateinit var backButton: MaterialButton
    private lateinit var initButton: MaterialButton
    private lateinit var pauseButton: MaterialButton
    private lateinit var resumeButton: MaterialButton
    private lateinit var stopButton: MaterialButton
    private lateinit var statusTextView: TextView
    
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_controls
    }
    
    override fun initializeActivity() {
        initializeViews()
        setupClickListeners()
    }
    
    private fun initializeViews() {
        backButton = findViewById(R.id.backButton)
        initButton = findViewById(R.id.initButton)
        pauseButton = findViewById(R.id.pauseButton)
        resumeButton = findViewById(R.id.resumeButton)
        stopButton = findViewById(R.id.stopButton)
        statusTextView = findViewById(R.id.statusTextView)
    }
    
    private fun setupClickListeners() {
        backButton.setOnClickListener {
            finish()
        }
        
        initButton.setOnClickListener {
            onInitButtonClicked()
        }
        
        pauseButton.setOnClickListener {
            onPauseButtonClicked()
        }
        
        resumeButton.setOnClickListener {
            onResumeButtonClicked()
        }
        
        stopButton.setOnClickListener {
            onStopButtonClicked()
        }
    }
    
    private fun onInitButtonClicked() {
        statusTextView.text = "🚀 Init button clicked"
        Toast.makeText(this, "🚀 Init clicked", Toast.LENGTH_SHORT).show()
    }
    
    private fun onPauseButtonClicked() {
        statusTextView.text = "⏸️ Pause button clicked"
        Toast.makeText(this, "⏸️ Pause clicked", Toast.LENGTH_SHORT).show()
    }
    
    private fun onResumeButtonClicked() {
        statusTextView.text = "▶️ Resume button clicked"
        Toast.makeText(this, "▶️ Resume clicked", Toast.LENGTH_SHORT).show()
    }
    
    private fun onStopButtonClicked() {
        statusTextView.text = "⏹️ Stop button clicked"
        Toast.makeText(this, "⏹️ Stop clicked", Toast.LENGTH_SHORT).show()
    }
} 