package com.example.helloworld

import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import android.view.animation.TranslateAnimation
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import kotlin.math.abs
import android.os.Handler
import android.os.Looper
import android.view.GestureDetector
import android.content.Context

class AnimationsActivity : BaseActivity() {
    
    private lateinit var backButton: MaterialButton
    private lateinit var animatedObject: ImageView
    private lateinit var statusTextView: TextView
    private lateinit var resetButton: MaterialButton
    
    // Gesture detectors
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private lateinit var gestureDetector: GestureDetector
    
    // Touch tracking for rotation
    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private var currentRotation = 0f
    private var currentScale = 1f
    
    // Animation state
    private var isAnimating = false
    
    // Long press handler
    private val longPressHandler = Handler(Looper.getMainLooper())
    private var longPressRunnable: Runnable? = null
    
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_animations
    }
    
    override fun initializeActivity() {
        initializeViews()
        setupGestureDetectors()
        setupClickListeners()
        setupTouchListeners()
    }
    
    private fun initializeViews() {
        backButton = findViewById(R.id.backButton)
        animatedObject = findViewById(R.id.animatedObject)
        statusTextView = findViewById(R.id.statusTextView)
        resetButton = findViewById(R.id.resetButton)
    }
    
    private fun setupGestureDetectors() {
        // Scale gesture detector for pinch zoom
        scaleGestureDetector = ScaleGestureDetector(this, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                if (!isAnimating) {
                    currentScale *= detector.scaleFactor
                    currentScale = currentScale.coerceIn(0.5f, 3.0f) // Limit scale range
                    
                    animatedObject.scaleX = currentScale
                    animatedObject.scaleY = currentScale
                    
                    updateStatus("Pinch zoom: ${String.format("%.1f", currentScale)}x")
                }
                return true
            }
        })
        
        // Gesture detector for other gestures
        gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                if (!isAnimating) {
                    performBounceAnimation()
                }
                return true
            }
            
            override fun onLongPress(e: MotionEvent) {
                if (!isAnimating) {
                    performPulseAnimation()
                }
            }
        })
    }
    
    private fun setupClickListeners() {
        backButton.setOnClickListener {
            finish()
        }
        
        resetButton.setOnClickListener {
            resetObject()
        }
    }
    
    private fun setupTouchListeners() {
        animatedObject.setOnTouchListener { view, event ->
            // Handle scale gestures
            scaleGestureDetector.onTouchEvent(event)
            
            // Handle other gestures
            gestureDetector.onTouchEvent(event)
            
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastTouchX = event.x
                    lastTouchY = event.y
                    
                    // Start long press detection
                    longPressRunnable = Runnable {
                        if (!isAnimating) {
                            performShakeAnimation()
                        }
                    }
                    longPressHandler.postDelayed(longPressRunnable!!, 1000) // 1 second for shake
                    
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    if (!isAnimating && !scaleGestureDetector.isInProgress) {
                        // Calculate rotation based on touch movement
                        val deltaX = event.x - lastTouchX
                        val deltaY = event.y - lastTouchY
                        
                        if (abs(deltaX) > 10 || abs(deltaY) > 10) {
                            val angle = Math.toDegrees(kotlin.math.atan2(deltaY.toDouble(), deltaX.toDouble())).toFloat()
                            currentRotation += angle * 0.1f
                            
                            animatedObject.rotation = currentRotation
                            updateStatus("Manual rotation: ${String.format("%.0f", currentRotation)}°")
                        }
                        
                        lastTouchX = event.x
                        lastTouchY = event.y
                    }
                    true
                }
                MotionEvent.ACTION_UP -> {
                    // Cancel long press
                    longPressRunnable?.let { longPressHandler.removeCallbacks(it) }
                    
                    // Check for quick tap (fade animation)
                    if (abs(event.x - lastTouchX) < 10 && abs(event.y - lastTouchY) < 10) {
                        if (!isAnimating) {
                            performFadeAnimation()
                        }
                    }
                    
                    // Check for swipe gesture (slide animation)
                    val deltaX = event.x - lastTouchX
                    val deltaY = event.y - lastTouchY
                    if (abs(deltaX) > 50 || abs(deltaY) > 50) {
                        if (!isAnimating) {
                            performSlideAnimation()
                        }
                    }
                    
                    true
                }
                else -> false
            }
        }
    }
    
    // Animation Methods
    private fun performRotateAnimation() {
        isAnimating = true
        updateStatus("🔄 Auto-rotating object...")
        
        val rotateAnimation = RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 1000
            repeatCount = 0
            fillAfter = true
        }
        
        rotateAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                currentRotation += 360f
                isAnimating = false
                updateStatus("✅ Auto-rotation complete!")
            }
        })
        
        animatedObject.startAnimation(rotateAnimation)
    }
    
    private fun performBounceAnimation() {
        isAnimating = true
        updateStatus("⚡ Double-tap bounce!")
        
        val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce_animation)
        bounceAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                isAnimating = false
                updateStatus("✅ Bounce complete!")
            }
        })
        
        animatedObject.startAnimation(bounceAnimation)
    }
    
    private fun performFadeAnimation() {
        isAnimating = true
        updateStatus("👻 Tap fade effect!")
        
        val fadeOut = AlphaAnimation(1.0f, 0.0f).apply {
            duration = 500
            fillAfter = true
        }
        
        val fadeIn = AlphaAnimation(0.0f, 1.0f).apply {
            duration = 500
            startOffset = 500
        }
        
        val animationSet = AnimationSet(true).apply {
            addAnimation(fadeOut)
            addAnimation(fadeIn)
        }
        
        animationSet.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                isAnimating = false
                updateStatus("✅ Fade complete!")
            }
        })
        
        animatedObject.startAnimation(animationSet)
    }
    
    private fun performSlideAnimation() {
        isAnimating = true
        updateStatus("➡️ Swipe slide effect!")
        
        val slideRight = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0.3f,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f
        ).apply {
            duration = 500
            fillAfter = true
        }
        
        val slideLeft = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0.3f,
            Animation.RELATIVE_TO_SELF, -0.3f,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f
        ).apply {
            duration = 1000
            startOffset = 500
        }
        
        val slideBack = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, -0.3f,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f
        ).apply {
            duration = 500
            startOffset = 1500
        }
        
        val animationSet = AnimationSet(true).apply {
            addAnimation(slideRight)
            addAnimation(slideLeft)
            addAnimation(slideBack)
        }
        
        animationSet.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                isAnimating = false
                updateStatus("✅ Slide complete!")
            }
        })
        
        animatedObject.startAnimation(animationSet)
    }
    
    private fun performPulseAnimation() {
        isAnimating = true
        updateStatus("💓 Long press pulse!")
        
        val pulseAnimation = AnimationUtils.loadAnimation(this, R.anim.pulse_animation)
        pulseAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                isAnimating = false
                updateStatus("✅ Pulse complete!")
            }
        })
        
        animatedObject.startAnimation(pulseAnimation)
    }
    
    private fun performShakeAnimation() {
        isAnimating = true
        updateStatus("📳 Extended long press shake!")
        
        val shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake_animation)
        shakeAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                isAnimating = false
                updateStatus("✅ Shake complete!")
            }
        })
        
        animatedObject.startAnimation(shakeAnimation)
    }
    
    private fun performFlipAnimation() {
        isAnimating = true
        updateStatus("🔄 Flip effect!")
        
        val flipAnimation = AnimationUtils.loadAnimation(this, R.anim.flip_animation)
        flipAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                isAnimating = false
                updateStatus("✅ Flip complete!")
            }
        })
        
        animatedObject.startAnimation(flipAnimation)
    }
    
    private fun performEnhanceAnimation() {
        isAnimating = true
        updateStatus("✨ Enhance effect!")
        
        val enhanceAnimation = AnimationUtils.loadAnimation(this, R.anim.enhance_animation)
        enhanceAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                isAnimating = false
                updateStatus("✅ Enhance complete!")
            }
        })
        
        animatedObject.startAnimation(enhanceAnimation)
    }
    
    private fun resetObject() {
        // Stop any ongoing animation
        animatedObject.clearAnimation()
        
        // Cancel any pending long press
        longPressRunnable?.let { longPressHandler.removeCallbacks(it) }
        
        // Reset all properties
        currentRotation = 0f
        currentScale = 1f
        isAnimating = false
        
        animatedObject.rotation = 0f
        animatedObject.scaleX = 1f
        animatedObject.scaleY = 1f
        animatedObject.alpha = 1f
        animatedObject.translationX = 0f
        animatedObject.translationY = 0f
        
        updateStatus("🔄 Object reset! Try these gestures:\n• Pinch to zoom\n• Drag to rotate\n• Tap to fade\n• Double-tap to bounce\n• Long press to pulse\n• Extended long press to shake\n• Swipe to slide")
        Toast.makeText(this, "Object reset!", Toast.LENGTH_SHORT).show()
    }
    
    private fun updateStatus(message: String) {
        statusTextView.text = message
    }
} 