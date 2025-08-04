package com.example.helloworld

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.view.View
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.*
import java.util.*
import java.io.File
import java.io.IOException
import java.lang.ArithmeticException
import java.lang.ArrayIndexOutOfBoundsException
import java.lang.ClassCastException
import java.lang.NullPointerException
import java.lang.NumberFormatException
import java.lang.OutOfMemoryError
import java.lang.StackOverflowError
import java.lang.StringIndexOutOfBoundsException
import java.lang.UnsupportedOperationException
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import java.lang.SecurityException
import java.lang.IndexOutOfBoundsException
import java.lang.NegativeArraySizeException
import java.lang.ClassNotFoundException
import java.lang.NoSuchMethodException
import java.lang.NoSuchFieldException
import java.lang.InstantiationException
import java.lang.IllegalAccessException
import java.lang.reflect.InvocationTargetException

class GenerateErrorsActivity : BaseActivity() {
    
    private lateinit var backButton: MaterialButton
    private lateinit var statusTextView: TextView
    private lateinit var flameImageView: ImageView
    private lateinit var errorCountTextView: TextView
    
    private var errorCount = 0
    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_generate_errors
    }
    
    override fun initializeActivity() {
        initializeViews()
        setupClickListeners()
        updateErrorCount()
    }
    
    private fun initializeViews() {
        backButton = findViewById(R.id.backButton)
        statusTextView = findViewById(R.id.statusTextView)
        flameImageView = findViewById(R.id.flameImageView)
        errorCountTextView = findViewById(R.id.errorCountTextView)
    }
    
    private fun setupClickListeners() {
        backButton.setOnClickListener {
            finish()
        }
        
        // Exception Errors
        findViewById<MaterialButton>(R.id.nullPointerButton).setOnClickListener {
            triggerNullPointerException()
        }
        
        findViewById<MaterialButton>(R.id.arrayIndexButton).setOnClickListener {
            triggerArrayIndexOutOfBoundsException()
        }
        
        findViewById<MaterialButton>(R.id.classCastButton).setOnClickListener {
            triggerClassCastException()
        }
        
        findViewById<MaterialButton>(R.id.numberFormatButton).setOnClickListener {
            triggerNumberFormatException()
        }
        
        findViewById<MaterialButton>(R.id.illegalArgumentButton).setOnClickListener {
            triggerIllegalArgumentException()
        }
        
        findViewById<MaterialButton>(R.id.illegalStateButton).setOnClickListener {
            triggerIllegalStateException()
        }
        
        findViewById<MaterialButton>(R.id.securityExceptionButton).setOnClickListener {
            triggerSecurityException()
        }
        
        findViewById<MaterialButton>(R.id.unsupportedOperationButton).setOnClickListener {
            triggerUnsupportedOperationException()
        }
        
        // Arithmetic Errors
        findViewById<MaterialButton>(R.id.divisionByZeroButton).setOnClickListener {
            triggerDivisionByZero()
        }
        
        findViewById<MaterialButton>(R.id.overflowButton).setOnClickListener {
            triggerIntegerOverflow()
        }
        
        findViewById<MaterialButton>(R.id.underflowButton).setOnClickListener {
            triggerIntegerUnderflow()
        }
        
        // Range Errors
        findViewById<MaterialButton>(R.id.stringIndexButton).setOnClickListener {
            triggerStringIndexOutOfBoundsException()
        }
        
        findViewById<MaterialButton>(R.id.negativeArrayButton).setOnClickListener {
            triggerNegativeArraySizeException()
        }
        
        findViewById<MaterialButton>(R.id.indexOutOfBoundsButton).setOnClickListener {
            triggerIndexOutOfBoundsException()
        }
        
        // Memory Errors
        findViewById<MaterialButton>(R.id.outOfMemoryButton).setOnClickListener {
            triggerOutOfMemoryError()
        }
        
        findViewById<MaterialButton>(R.id.stackOverflowButton).setOnClickListener {
            triggerStackOverflowError()
        }
        
        // Reflection Errors
        findViewById<MaterialButton>(R.id.classNotFoundButton).setOnClickListener {
            triggerClassNotFoundException()
        }
        
        findViewById<MaterialButton>(R.id.noSuchMethodButton).setOnClickListener {
            triggerNoSuchMethodException()
        }
        
        findViewById<MaterialButton>(R.id.noSuchFieldButton).setOnClickListener {
            triggerNoSuchFieldException()
        }
        
        findViewById<MaterialButton>(R.id.instantiationButton).setOnClickListener {
            triggerInstantiationException()
        }
        
        findViewById<MaterialButton>(R.id.illegalAccessButton).setOnClickListener {
            triggerIllegalAccessException()
        }
        
        findViewById<MaterialButton>(R.id.invocationTargetButton).setOnClickListener {
            triggerInvocationTargetException()
        }
        
        // File System Errors
        findViewById<MaterialButton>(R.id.fileNotFoundButton).setOnClickListener {
            triggerFileNotFoundException()
        }
        
        findViewById<MaterialButton>(R.id.ioExceptionButton).setOnClickListener {
            triggerIOException()
        }
        
        // Custom Errors
        findViewById<MaterialButton>(R.id.customExceptionButton).setOnClickListener {
            triggerCustomException()
        }
        
        findViewById<MaterialButton>(R.id.concurrentModificationButton).setOnClickListener {
            triggerConcurrentModificationException()
        }
        
        findViewById<MaterialButton>(R.id.timeoutExceptionButton).setOnClickListener {
            triggerTimeoutException()
        }
        
        findViewById<MaterialButton>(R.id.networkExceptionButton).setOnClickListener {
            triggerNetworkException()
        }
    }
    
    // Exception Errors
    private fun triggerNullPointerException() {
        updateStatus("🔥 Triggering NullPointerException...")
        playFlameAnimation(findViewById(R.id.nullPointerButton))
        
        try {
            val nullString: String? = null
            val length = nullString!!.length // This will throw NullPointerException
        } catch (e: NullPointerException) {
            handleError("NullPointerException", e.message ?: "Object is null")
        }
    }
    
    private fun triggerArrayIndexOutOfBoundsException() {
        updateStatus("🔥 Triggering ArrayIndexOutOfBoundsException...")
        playFlameAnimation(findViewById(R.id.arrayIndexButton))
        
        try {
            val array = arrayOf(1, 2, 3)
            val element = array[10] // This will throw ArrayIndexOutOfBoundsException
        } catch (e: ArrayIndexOutOfBoundsException) {
            handleError("ArrayIndexOutOfBoundsException", e.message ?: "Array index out of bounds")
        }
    }
    
    private fun triggerClassCastException() {
        updateStatus("🔥 Triggering ClassCastException...")
        playFlameAnimation(findViewById(R.id.classCastButton))
        
        try {
            val obj: Any = "This is a string"
            val number = obj as Int // This will throw ClassCastException
        } catch (e: ClassCastException) {
            handleError("ClassCastException", e.message ?: "Cannot cast object to target type")
        }
    }
    
    private fun triggerNumberFormatException() {
        updateStatus("🔥 Triggering NumberFormatException...")
        playFlameAnimation(findViewById(R.id.numberFormatButton))
        
        try {
            val invalidNumber = "not_a_number"
            val number = invalidNumber.toInt() // This will throw NumberFormatException
        } catch (e: NumberFormatException) {
            handleError("NumberFormatException", e.message ?: "Invalid number format")
        }
    }
    
    private fun triggerIllegalArgumentException() {
        updateStatus("🔥 Triggering IllegalArgumentException...")
        playFlameAnimation(findViewById(R.id.illegalArgumentButton))
        
        try {
            throw IllegalArgumentException("This is an illegal argument")
        } catch (e: IllegalArgumentException) {
            handleError("IllegalArgumentException", e.message ?: "Invalid argument provided")
        }
    }
    
    private fun triggerIllegalStateException() {
        updateStatus("🔥 Triggering IllegalStateException...")
        playFlameAnimation(findViewById(R.id.illegalStateButton))
        
        try {
            throw IllegalStateException("Object is in an illegal state")
        } catch (e: IllegalStateException) {
            handleError("IllegalStateException", e.message ?: "Object state is invalid")
        }
    }
    
    private fun triggerSecurityException() {
        updateStatus("🔥 Triggering SecurityException...")
        playFlameAnimation(findViewById(R.id.securityExceptionButton))
        
        try {
            throw SecurityException("Security violation detected")
        } catch (e: SecurityException) {
            handleError("SecurityException", e.message ?: "Security policy violation")
        }
    }
    
    private fun triggerUnsupportedOperationException() {
        updateStatus("🔥 Triggering UnsupportedOperationException...")
        playFlameAnimation(findViewById(R.id.unsupportedOperationButton))
        
        try {
            throw UnsupportedOperationException("This operation is not supported")
        } catch (e: UnsupportedOperationException) {
            handleError("UnsupportedOperationException", e.message ?: "Operation not supported")
        }
    }
    
    // Arithmetic Errors
    private fun triggerDivisionByZero() {
        updateStatus("🔥 Triggering Division by Zero...")
        playFlameAnimation(findViewById(R.id.divisionByZeroButton))
        
        try {
            val result = 10 / 0 // This will throw ArithmeticException
        } catch (e: ArithmeticException) {
            handleError("ArithmeticException", e.message ?: "Division by zero")
        }
    }
    
    private fun triggerIntegerOverflow() {
        updateStatus("🔥 Triggering Integer Overflow...")
        playFlameAnimation(findViewById(R.id.overflowButton))
        
        try {
            var maxInt = Int.MAX_VALUE
            maxInt += 1 // This will cause overflow
            updateStatus("✅ Integer overflow occurred: $maxInt")
            incrementErrorCount()
        } catch (e: Exception) {
            handleError("IntegerOverflow", e.message ?: "Integer overflow")
        }
    }
    
    private fun triggerIntegerUnderflow() {
        updateStatus("🔥 Triggering Integer Underflow...")
        playFlameAnimation(findViewById(R.id.underflowButton))
        
        try {
            var minInt = Int.MIN_VALUE
            minInt -= 1 // This will cause underflow
            updateStatus("✅ Integer underflow occurred: $minInt")
            incrementErrorCount()
        } catch (e: Exception) {
            handleError("IntegerUnderflow", e.message ?: "Integer underflow")
        }
    }
    
    // Range Errors
    private fun triggerStringIndexOutOfBoundsException() {
        updateStatus("🔥 Triggering StringIndexOutOfBoundsException...")
        playFlameAnimation(findViewById(R.id.stringIndexButton))
        
        try {
            val str = "Hello"
            val char = str[10] // This will throw StringIndexOutOfBoundsException
        } catch (e: StringIndexOutOfBoundsException) {
            handleError("StringIndexOutOfBoundsException", e.message ?: "String index out of bounds")
        }
    }
    
    private fun triggerNegativeArraySizeException() {
        updateStatus("🔥 Triggering NegativeArraySizeException...")
        playFlameAnimation(findViewById(R.id.negativeArrayButton))
        
        try {
            val array = Array(-5) { 0 } // This will throw NegativeArraySizeException
        } catch (e: NegativeArraySizeException) {
            handleError("NegativeArraySizeException", e.message ?: "Negative array size")
        }
    }
    
    private fun triggerIndexOutOfBoundsException() {
        updateStatus("🔥 Triggering IndexOutOfBoundsException...")
        playFlameAnimation(findViewById(R.id.indexOutOfBoundsButton))
        
        try {
            val list = listOf(1, 2, 3)
            val element = list[10] // This will throw IndexOutOfBoundsException
        } catch (e: IndexOutOfBoundsException) {
            handleError("IndexOutOfBoundsException", e.message ?: "Index out of bounds")
        }
    }
    
    // Memory Errors
    private fun triggerOutOfMemoryError() {
        updateStatus("🔥 Triggering OutOfMemoryError...")
        playFlameAnimation(findViewById(R.id.outOfMemoryButton))
        
        try {
            val hugeArray = Array(Int.MAX_VALUE) { 0 } // This will throw OutOfMemoryError
        } catch (e: OutOfMemoryError) {
            handleError("OutOfMemoryError", e.message ?: "Out of memory")
        }
    }
    
    private fun triggerStackOverflowError() {
        updateStatus("🔥 Triggering StackOverflowError...")
        playFlameAnimation(findViewById(R.id.stackOverflowButton))
        
        try {
            recursiveFunction(0) // This will cause stack overflow
        } catch (e: StackOverflowError) {
            handleError("StackOverflowError", e.message ?: "Stack overflow")
        }
    }
    
    private fun recursiveFunction(count: Int): Int {
        return recursiveFunction(count + 1) // Infinite recursion
    }
    
    // Reflection Errors
    private fun triggerClassNotFoundException() {
        updateStatus("🔥 Triggering ClassNotFoundException...")
        playFlameAnimation(findViewById(R.id.classNotFoundButton))
        
        try {
            val clazz = Class.forName("com.example.NonExistentClass")
        } catch (e: ClassNotFoundException) {
            handleError("ClassNotFoundException", e.message ?: "Class not found")
        }
    }
    
    private fun triggerNoSuchMethodException() {
        updateStatus("🔥 Triggering NoSuchMethodException...")
        playFlameAnimation(findViewById(R.id.noSuchMethodButton))
        
        try {
            val clazz = String::class.java
            val method = clazz.getMethod("nonExistentMethod")
        } catch (e: NoSuchMethodException) {
            handleError("NoSuchMethodException", e.message ?: "Method not found")
        }
    }
    
    private fun triggerNoSuchFieldException() {
        updateStatus("🔥 Triggering NoSuchFieldException...")
        playFlameAnimation(findViewById(R.id.noSuchFieldButton))
        
        try {
            val clazz = String::class.java
            val field = clazz.getField("nonExistentField")
        } catch (e: NoSuchFieldException) {
            handleError("NoSuchFieldException", e.message ?: "Field not found")
        }
    }
    
    private fun triggerInstantiationException() {
        updateStatus("🔥 Triggering InstantiationException...")
        playFlameAnimation(findViewById(R.id.instantiationButton))
        
        try {
            val clazz = String::class.java
            val instance = clazz.newInstance()
        } catch (e: InstantiationException) {
            handleError("InstantiationException", e.message ?: "Cannot instantiate class")
        }
    }
    
    private fun triggerIllegalAccessException() {
        updateStatus("🔥 Triggering IllegalAccessException...")
        playFlameAnimation(findViewById(R.id.illegalAccessButton))
        
        try {
            val clazz = String::class.java
            val constructor = clazz.getDeclaredConstructor()
            constructor.isAccessible = false
            val instance = constructor.newInstance()
        } catch (e: IllegalAccessException) {
            handleError("IllegalAccessException", e.message ?: "Illegal access to method/field")
        }
    }
    
    private fun triggerInvocationTargetException() {
        updateStatus("🔥 Triggering InvocationTargetException...")
        playFlameAnimation(findViewById(R.id.invocationTargetButton))
        
        try {
            val clazz = String::class.java
            val method = clazz.getMethod("substring", Int::class.java)
            val result = method.invoke("test", -1) // This will cause an exception
        } catch (e: InvocationTargetException) {
            handleError("InvocationTargetException", e.message ?: "Exception thrown by invoked method")
        }
    }
    
    // File System Errors
    private fun triggerFileNotFoundException() {
        updateStatus("🔥 Triggering FileNotFoundException...")
        playFlameAnimation(findViewById(R.id.fileNotFoundButton))
        
        try {
            val file = File("/non/existent/path/file.txt")
            if (!file.exists()) {
                throw java.io.FileNotFoundException("File not found: ${file.absolutePath}")
            }
        } catch (e: java.io.FileNotFoundException) {
            handleError("FileNotFoundException", e.message ?: "File not found")
        }
    }
    
    private fun triggerIOException() {
        updateStatus("🔥 Triggering IOException...")
        playFlameAnimation(findViewById(R.id.ioExceptionButton))
        
        try {
            throw IOException("I/O operation failed")
        } catch (e: IOException) {
            handleError("IOException", e.message ?: "I/O error occurred")
        }
    }
    
    // Custom Errors
    private fun triggerCustomException() {
        updateStatus("🔥 Triggering CustomException...")
        playFlameAnimation(findViewById(R.id.customExceptionButton))
        
        try {
            throw CustomTestException("This is a custom test exception")
        } catch (e: CustomTestException) {
            handleError("CustomTestException", e.message ?: "Custom exception occurred")
        }
    }
    
    private fun triggerConcurrentModificationException() {
        updateStatus("🔥 Triggering ConcurrentModificationException...")
        playFlameAnimation(findViewById(R.id.concurrentModificationButton))
        
        try {
            val list = mutableListOf(1, 2, 3, 4, 5)
            for (item in list) {
                if (item == 3) {
                    list.add(6) // This will throw ConcurrentModificationException
                }
            }
        } catch (e: ConcurrentModificationException) {
            handleError("ConcurrentModificationException", e.message ?: "Concurrent modification detected")
        }
    }
    
    private fun triggerTimeoutException() {
        updateStatus("🔥 Triggering TimeoutException...")
        playFlameAnimation(findViewById(R.id.timeoutExceptionButton))
        
        try {
            throw java.util.concurrent.TimeoutException("Operation timed out")
        } catch (e: java.util.concurrent.TimeoutException) {
            handleError("TimeoutException", e.message ?: "Operation timed out")
        }
    }
    
    private fun triggerNetworkException() {
        updateStatus("🔥 Triggering NetworkException...")
        playFlameAnimation(findViewById(R.id.networkExceptionButton))
        
        try {
            throw java.net.ConnectException("Connection refused")
        } catch (e: java.net.ConnectException) {
            handleError("NetworkException", e.message ?: "Network connection failed")
        }
    }
    
    private fun handleError(errorType: String, message: String) {
        errorCount++
        updateErrorCount()
        updateStatus("❌ $errorType: $message")
        Toast.makeText(this, "$errorType triggered!", Toast.LENGTH_SHORT).show()
    }
    
    private fun updateStatus(message: String) {
        statusTextView.text = message
    }
    
    private fun updateErrorCount() {
        errorCountTextView.text = "Errors Generated: $errorCount"
    }
    
    private fun incrementErrorCount() {
        errorCount++
        updateErrorCount()
    }
    
    private fun playFlameAnimation(buttonView: View? = null) {
        // Position flame at button location if provided
        if (buttonView != null) {
            val location = IntArray(2)
            buttonView.getLocationOnScreen(location)
            
            // Calculate center of the button
            val buttonCenterX = location[0] + buttonView.width / 2
            val buttonCenterY = location[1] + buttonView.height / 2
            
            // Calculate flame position (center flame on button)
            val flameX = buttonCenterX - flameImageView.width / 2
            val flameY = buttonCenterY - flameImageView.height / 2
            
            // Set flame position before making it visible
            flameImageView.x = flameX.toFloat()
            flameImageView.y = flameY.toFloat()
        } else {
            // Default position (center of screen)
            val displayMetrics = resources.displayMetrics
            val screenWidth = displayMetrics.widthPixels
            val screenHeight = displayMetrics.heightPixels
            
            flameImageView.x = (screenWidth / 2 - flameImageView.width / 2).toFloat()
            flameImageView.y = (screenHeight / 2 - flameImageView.height / 2).toFloat()
        }
        
        // Make flame visible after positioning
        flameImageView.visibility = View.VISIBLE
        
        val flameAnimation = AnimationUtils.loadAnimation(this, R.anim.flame_animation)
        
        // Set animation listener to hide the flame after animation completes
        flameAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                flameImageView.visibility = View.GONE
            }
        })
        
        flameImageView.startAnimation(flameAnimation)
    }
    
    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}

// Custom Exception Class
class CustomTestException(message: String) : Exception(message) 