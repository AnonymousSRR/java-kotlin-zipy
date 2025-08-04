package com.example.helloworld

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.*

class PayActivity : BaseActivity() {
    
    private lateinit var backButton: MaterialButton
    private lateinit var payButton: MaterialButton
    private lateinit var statusTextView: TextView
    
    // Credit card form fields
    private lateinit var cardNumberEditText: EditText
    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var expiryDateEditText: EditText
    private lateinit var cvvEditText: EditText
    private lateinit var billingAddressEditText: EditText
    private lateinit var cityEditText: EditText
    private lateinit var zipCodeEditText: EditText
    private lateinit var amountEditText: EditText
    
    // Card display
    private lateinit var cardNumberDisplay: TextView
    private lateinit var cardHolderDisplay: TextView
    private lateinit var cardExpiryDisplay: TextView
    
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_pay
    }
    
    override fun initializeActivity() {
        initializeViews()
        setupClickListeners()
        setupTextWatchers()
    }
    
    private fun initializeViews() {
        backButton = findViewById(R.id.backButton)
        payButton = findViewById(R.id.payButton)
        statusTextView = findViewById(R.id.statusTextView)
        
        // Initialize form fields
        cardNumberEditText = findViewById(R.id.cardNumberEditText)
        firstNameEditText = findViewById(R.id.firstNameEditText)
        lastNameEditText = findViewById(R.id.lastNameEditText)
        expiryDateEditText = findViewById(R.id.expiryDateEditText)
        cvvEditText = findViewById(R.id.cvvEditText)
        billingAddressEditText = findViewById(R.id.billingAddressEditText)
        cityEditText = findViewById(R.id.cityEditText)
        zipCodeEditText = findViewById(R.id.zipCodeEditText)
        amountEditText = findViewById(R.id.amountEditText)
        
        // Initialize card display
        cardNumberDisplay = findViewById(R.id.cardNumberDisplay)
        cardHolderDisplay = findViewById(R.id.cardHolderDisplay)
        cardExpiryDisplay = findViewById(R.id.cardExpiryDisplay)
    }
    
    private fun setupClickListeners() {
        backButton.setOnClickListener {
            finish()
        }
        
        payButton.setOnClickListener {
            processPayment()
        }
    }
    
    private fun setupTextWatchers() {
        // Card number formatting
        cardNumberEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val cardNumber = s.toString().replace(" ", "")
                val formatted = formatCardNumber(cardNumber)
                if (formatted != s.toString()) {
                    cardNumberEditText.setText(formatted)
                    cardNumberEditText.setSelection(formatted.length)
                }
                updateCardDisplay()
            }
        })
        
        // Expiry date formatting
        expiryDateEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val expiry = s.toString().replace("/", "")
                val formatted = formatExpiryDate(expiry)
                if (formatted != s.toString()) {
                    expiryDateEditText.setText(formatted)
                    expiryDateEditText.setSelection(formatted.length)
                }
                updateCardDisplay()
            }
        })
        
        // Name fields
        firstNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                updateCardDisplay()
            }
        })
        
        lastNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                updateCardDisplay()
            }
        })
    }
    
    private fun formatCardNumber(cardNumber: String): String {
        val cleaned = cardNumber.replace(" ", "")
        val groups = cleaned.chunked(4)
        return groups.joinToString(" ")
    }
    
    private fun formatExpiryDate(expiry: String): String {
        if (expiry.length >= 2) {
            return "${expiry.substring(0, 2)}/${expiry.substring(2)}"
        }
        return expiry
    }
    
    private fun updateCardDisplay() {
        val cardNumber = cardNumberEditText.text.toString()
        val firstName = firstNameEditText.text.toString()
        val lastName = lastNameEditText.text.toString()
        val expiry = expiryDateEditText.text.toString()
        
        cardNumberDisplay.text = cardNumber.ifEmpty { "**** **** **** ****" }
        cardHolderDisplay.text = "${firstName.ifEmpty { "CARD" }} ${lastName.ifEmpty { "HOLDER" }}".uppercase()
        cardExpiryDisplay.text = expiry.ifEmpty { "MM/YY" }
    }
    
    private fun processPayment() {
        val cardNumber = cardNumberEditText.text.toString().replace(" ", "")
        val firstName = firstNameEditText.text.toString()
        val lastName = lastNameEditText.text.toString()
        val expiry = expiryDateEditText.text.toString()
        val cvv = cvvEditText.text.toString()
        val address = billingAddressEditText.text.toString()
        val city = cityEditText.text.toString()
        val zipCode = zipCodeEditText.text.toString()
        val amount = amountEditText.text.toString()
        
        // Basic validation
        if (cardNumber.length < 16) {
            showError("Please enter a valid card number")
            return
        }
        
        if (firstName.isEmpty() || lastName.isEmpty()) {
            showError("Please enter cardholder name")
            return
        }
        
        if (expiry.length < 5) {
            showError("Please enter expiry date (MM/YY)")
            return
        }
        
        if (cvv.length < 3) {
            showError("Please enter CVV")
            return
        }
        
        if (address.isEmpty() || city.isEmpty() || zipCode.isEmpty()) {
            showError("Please complete billing address")
            return
        }
        
        if (amount.isEmpty() || amount.toDoubleOrNull() == null) {
            showError("Please enter a valid amount")
            return
        }
        
        // Simulate payment processing
        showProcessing()
        
        // Simulate network delay
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            val success = kotlin.random.Random.nextBoolean()
            if (success) {
                showSuccess()
            } else {
                showError("Payment failed. Please try again.")
            }
        }, 2000)
    }
    
    private fun showProcessing() {
        statusTextView.text = "Processing payment..."
        payButton.isEnabled = false
        payButton.text = "Processing..."
    }
    
    private fun showSuccess() {
        val amount = amountEditText.text.toString()
        statusTextView.text = "Payment successful!\nAmount: $${amount}\nTransaction ID: ${generateTransactionId()}"
        payButton.isEnabled = true
        payButton.text = "Pay"
        Toast.makeText(this, "Payment successful!", Toast.LENGTH_LONG).show()
    }
    
    private fun showError(message: String) {
        statusTextView.text = "Error: $message"
        payButton.isEnabled = true
        payButton.text = "Pay"
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    
    private fun generateTransactionId(): String {
        val timestamp = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
        val random = (1000..9999).random()
        return "TXN$timestamp$random"
    }
} 