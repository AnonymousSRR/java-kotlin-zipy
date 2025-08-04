package com.example.helloworld

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import android.widget.Switch
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.ArrayAdapter
import com.google.android.material.button.MaterialButton

class SettingsActivity : BaseActivity() {
    
    private lateinit var backButton: MaterialButton
    private lateinit var saveButton: MaterialButton
    private lateinit var resetButton: MaterialButton
    private lateinit var statusTextView: TextView
    
    // Settings controls
    private lateinit var notificationsSwitch: Switch
    private lateinit var darkModeSwitch: Switch
    private lateinit var autoSaveSwitch: Switch
    private lateinit var soundEffectsSwitch: Switch
    private lateinit var vibrationSwitch: Switch
    private lateinit var brightnessSlider: SeekBar
    private lateinit var volumeSlider: SeekBar
    private lateinit var languageSpinner: Spinner
    private lateinit var themeSpinner: Spinner
    
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_settings
    }
    
    override fun initializeActivity() {
        initializeViews()
        setupClickListeners()
        setupSpinners()
        setupSliders()
    }
    
    private fun initializeViews() {
        backButton = findViewById(R.id.backButton)
        saveButton = findViewById(R.id.saveButton)
        resetButton = findViewById(R.id.resetButton)
        statusTextView = findViewById(R.id.statusTextView)
        
        // Initialize switches
        notificationsSwitch = findViewById(R.id.notificationsSwitch)
        darkModeSwitch = findViewById(R.id.darkModeSwitch)
        autoSaveSwitch = findViewById(R.id.autoSaveSwitch)
        soundEffectsSwitch = findViewById(R.id.soundEffectsSwitch)
        vibrationSwitch = findViewById(R.id.vibrationSwitch)
        
        // Initialize sliders
        brightnessSlider = findViewById(R.id.brightnessSlider)
        volumeSlider = findViewById(R.id.volumeSlider)
        
        // Initialize spinners
        languageSpinner = findViewById(R.id.languageSpinner)
        themeSpinner = findViewById(R.id.themeSpinner)
    }
    
    private fun setupClickListeners() {
        backButton.setOnClickListener {
            finish()
        }
        
        saveButton.setOnClickListener {
            saveSettings()
        }
        
        resetButton.setOnClickListener {
            resetSettings()
        }
        
        // Switch listeners
        notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            updateStatus("Notifications: ${if (isChecked) "ON" else "OFF"}")
        }
        
        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            updateStatus("Dark Mode: ${if (isChecked) "ON" else "OFF"}")
        }
        
        autoSaveSwitch.setOnCheckedChangeListener { _, isChecked ->
            updateStatus("Auto Save: ${if (isChecked) "ON" else "OFF"}")
        }
        
        soundEffectsSwitch.setOnCheckedChangeListener { _, isChecked ->
            updateStatus("Sound Effects: ${if (isChecked) "ON" else "OFF"}")
        }
        
        vibrationSwitch.setOnCheckedChangeListener { _, isChecked ->
            updateStatus("Vibration: ${if (isChecked) "ON" else "OFF"}")
        }
    }
    
    private fun setupSpinners() {
        // Language spinner
        val languages = arrayOf("English", "Spanish", "French", "German", "Chinese", "Japanese")
        val languageAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        languageSpinner.adapter = languageAdapter
        
        languageSpinner.setOnItemSelectedListener(object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                updateStatus("Language: ${languages[position]}")
            }
            
            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {}
        })
        
        // Theme spinner
        val themes = arrayOf("Default", "Light", "Dark", "Blue", "Green", "Purple")
        val themeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, themes)
        themeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        themeSpinner.adapter = themeAdapter
        
        themeSpinner.setOnItemSelectedListener(object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                updateStatus("Theme: ${themes[position]}")
            }
            
            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {}
        })
    }
    
    private fun setupSliders() {
        brightnessSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    updateStatus("Brightness: ${progress}%")
                }
            }
            
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        
        volumeSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    updateStatus("Volume: ${progress}%")
                }
            }
            
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
    
    private fun saveSettings() {
        val settings = StringBuilder()
        settings.append("Settings Saved:\n")
        settings.append("• Notifications: ${if (notificationsSwitch.isChecked) "ON" else "OFF"}\n")
        settings.append("• Dark Mode: ${if (darkModeSwitch.isChecked) "ON" else "OFF"}\n")
        settings.append("• Auto Save: ${if (autoSaveSwitch.isChecked) "ON" else "OFF"}\n")
        settings.append("• Sound Effects: ${if (soundEffectsSwitch.isChecked) "ON" else "OFF"}\n")
        settings.append("• Vibration: ${if (vibrationSwitch.isChecked) "ON" else "OFF"}\n")
        settings.append("• Brightness: ${brightnessSlider.progress}%\n")
        settings.append("• Volume: ${volumeSlider.progress}%\n")
        settings.append("• Language: ${languageSpinner.selectedItem}\n")
        settings.append("• Theme: ${themeSpinner.selectedItem}")
        
        statusTextView.text = settings.toString()
        Toast.makeText(this, "Settings saved successfully!", Toast.LENGTH_SHORT).show()
    }
    
    private fun resetSettings() {
        // Reset all switches to default
        notificationsSwitch.isChecked = true
        darkModeSwitch.isChecked = false
        autoSaveSwitch.isChecked = true
        soundEffectsSwitch.isChecked = true
        vibrationSwitch.isChecked = false
        
        // Reset sliders to default
        brightnessSlider.progress = 50
        volumeSlider.progress = 75
        
        // Reset spinners to default
        languageSpinner.setSelection(0)
        themeSpinner.setSelection(0)
        
        updateStatus("Settings reset to default values")
        Toast.makeText(this, "Settings reset to default!", Toast.LENGTH_SHORT).show()
    }
    
    private fun updateStatus(message: String) {
        statusTextView.text = message
    }
} 