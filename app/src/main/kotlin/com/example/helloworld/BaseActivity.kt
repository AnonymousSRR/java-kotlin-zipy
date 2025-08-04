package com.example.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

abstract class BaseActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Set the content view
        setContentView(getLayoutResourceId())
        
        // Initialize the activity
        initializeActivity()
    }
    
    abstract fun getLayoutResourceId(): Int
    
    abstract fun initializeActivity()
} 