package com.example.helloworld

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.*
import kotlin.random.Random

class GameView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val paint = Paint()
    private val earthPaint = Paint()
    private val asteroidPaint = Paint()
    private val trailPaint = Paint()
    private val arrowPaint = Paint()
    private val rubberBandPaint = Paint()
    
    private var earthX = 0f
    private var earthY = 0f
    private var earthVelocityX = 0f
    private var earthVelocityY = 0f
    private var earthRadius = 30f
    
    private var asteroidX = 0f
    private var asteroidY = 0f
    private var asteroidVelocityX = 0f
    private var asteroidRadius = 40f
    
    private var isEarthLaunched = false
    private var isGameActive = true
    private var isPulling = false
    private var pullStartX = 0f
    private var pullStartY = 0f
    private var currentPullX = 0f
    private var currentPullY = 0f
    
    private val gravity = 0.5f
    private val airResistance = 0.99f
    private val maxPullDistance = 250f
    private val forceMultiplier = 0.4f
    
    private val earthTrail = mutableListOf<Pair<Float, Float>>()
    private val maxTrailLength = 20
    
    private var gameListener: MiniGameActivity.GameListener? = null
    
    fun setOnGameListener(listener: MiniGameActivity.GameListener) {
        gameListener = listener
    }
    
    init {
        setupPaints()
        resetEarth()
        resetAsteroid()
    }
    
    private fun setupPaints() {
        // Earth paint (blue planet)
        earthPaint.apply {
            color = Color.rgb(30, 144, 255)
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        
        // Asteroid paint (brown/red)
        asteroidPaint.apply {
            color = Color.rgb(139, 69, 19)
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        
        // Trail paint
        trailPaint.apply {
            color = Color.rgb(30, 144, 255)
            style = Paint.Style.STROKE
            strokeWidth = 3f
            isAntiAlias = true
            alpha = 100
        }
        
        // Arrow paint (direction indicator)
        arrowPaint.apply {
            color = Color.rgb(255, 255, 0) // Yellow
            style = Paint.Style.STROKE
            strokeWidth = 5f
            isAntiAlias = true
        }
        
        // Rubber band paint
        rubberBandPaint.apply {
            color = Color.rgb(255, 255, 255) // White
            style = Paint.Style.STROKE
            strokeWidth = 3f
            isAntiAlias = true
            alpha = 150
        }
    }
    
    private fun resetEarth() {
        earthX = width / 2f
        earthY = height - 100f
        earthVelocityX = 0f
        earthVelocityY = 0f
        isEarthLaunched = false
        isPulling = false
        earthTrail.clear()
    }
    
    fun resetAsteroid() {
        asteroidX = Random.nextFloat() * (width - 200f) + 100f
        asteroidY = 100f
        asteroidVelocityX = (Random.nextFloat() - 0.5f) * 4f
        asteroidRadius = 40f
    }
    
    fun resetGame() {
        resetEarth()
        resetAsteroid()
        isGameActive = true
    }
    
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        resetEarth()
        resetAsteroid()
    }
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        // Draw background (space)
        canvas.drawColor(Color.rgb(10, 10, 30))
        
        // Draw stars
        drawStars(canvas)
        
        // Draw asteroid
        drawAsteroid(canvas)
        
        // Draw earth trail
        drawEarthTrail(canvas)
        
        // Draw rubber band if pulling
        if (isPulling && !isEarthLaunched) {
            drawRubberBand(canvas)
        }
        
        // Draw earth
        drawEarth(canvas)
        
        // Draw direction arrow if pulling
        if (isPulling && !isEarthLaunched) {
            drawDirectionArrow(canvas)
        }
        
        // Update physics
        updatePhysics()
        
        // Continue animation
        if (isGameActive) {
            invalidate()
        }
    }
    
    private fun drawStars(canvas: Canvas) {
        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        
        for (i in 0 until 50) {
            val x = (i * 37) % width
            val y = (i * 73) % height
            canvas.drawCircle(x.toFloat(), y.toFloat(), 1f, paint)
        }
    }
    
    private fun drawAsteroid(canvas: Canvas) {
        // Draw asteroid with irregular shape
        val path = Path()
        val centerX = asteroidX
        val centerY = asteroidY
        
        for (i in 0..360 step 30) {
            val angle = Math.toRadians(i.toDouble())
            val radius = asteroidRadius + Random.nextFloat() * 10f - 5f
            val x = centerX + (radius * cos(angle)).toFloat()
            val y = centerY + (radius * sin(angle)).toFloat()
            
            if (i == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }
        path.close()
        
        canvas.drawPath(path, asteroidPaint)
    }
    
    private fun drawEarth(canvas: Canvas) {
        // Draw Earth with continents
        canvas.drawCircle(earthX, earthY, earthRadius, earthPaint)
        
        // Draw continents (simple shapes)
        paint.color = Color.rgb(34, 139, 34)
        paint.style = Paint.Style.FILL
        
        // Simple continent shapes
        canvas.drawCircle(earthX - 10f, earthY - 5f, 8f, paint)
        canvas.drawCircle(earthX + 8f, earthY + 3f, 6f, paint)
    }
    
    private fun drawEarthTrail(canvas: Canvas) {
        if (earthTrail.size < 2) return
        
        val path = Path()
        path.moveTo(earthTrail[0].first, earthTrail[0].second)
        
        for (i in 1 until earthTrail.size) {
            path.lineTo(earthTrail[i].first, earthTrail[i].second)
        }
        
        canvas.drawPath(path, trailPaint)
    }
    
    private fun drawRubberBand(canvas: Canvas) {
        // Draw rubber band from Earth to pull position
        canvas.drawLine(earthX, earthY, currentPullX, currentPullY, rubberBandPaint)
    }
    
    private fun drawDirectionArrow(canvas: Canvas) {
        // Calculate direction vector
        val deltaX = earthX - currentPullX
        val deltaY = earthY - currentPullY
        val distance = sqrt(deltaX * deltaX + deltaY * deltaY)
        
        if (distance > 10f) {
            // Normalize direction
            val dirX = deltaX / distance
            val dirY = deltaY / distance
            
            // Arrow start position (from Earth)
            val arrowStartX = earthX
            val arrowStartY = earthY
            
            // Arrow end position
            val arrowLength = minOf(distance * 0.5f, 100f)
            val arrowEndX = arrowStartX + dirX * arrowLength
            val arrowEndY = arrowStartY + dirY * arrowLength
            
            // Draw arrow line
            canvas.drawLine(arrowStartX, arrowStartY, arrowEndX, arrowEndY, arrowPaint)
            
            // Draw arrow head
            val arrowHeadLength = 20f
            val arrowHeadAngle = 30f
            
            val angle = atan2(dirY, dirX)
            val headAngle1 = angle + Math.toRadians(arrowHeadAngle.toDouble())
            val headAngle2 = angle - Math.toRadians(arrowHeadAngle.toDouble())
            
            val head1X = arrowEndX - (arrowHeadLength * cos(headAngle1)).toFloat()
            val head1Y = arrowEndY - (arrowHeadLength * sin(headAngle1)).toFloat()
            val head2X = arrowEndX - (arrowHeadLength * cos(headAngle2)).toFloat()
            val head2Y = arrowEndY - (arrowHeadLength * sin(headAngle2)).toFloat()
            
            canvas.drawLine(arrowEndX, arrowEndY, head1X, head1Y, arrowPaint)
            canvas.drawLine(arrowEndX, arrowEndY, head2X, head2Y, arrowPaint)
        }
    }
    
    private fun updatePhysics() {
        if (isEarthLaunched) {
            // Update Earth physics
            earthVelocityY += gravity
            earthVelocityX *= airResistance
            earthVelocityY *= airResistance
            
            earthX += earthVelocityX
            earthY += earthVelocityY
            
            // Add to trail
            earthTrail.add(Pair(earthX, earthY))
            if (earthTrail.size > maxTrailLength) {
                earthTrail.removeAt(0)
            }
            
            // Check collision with asteroid
            val distance = sqrt((earthX - asteroidX).pow(2) + (earthY - asteroidY).pow(2))
            if (distance < earthRadius + asteroidRadius) {
                // Hit!
                gameListener?.onAsteroidHit()
                resetEarth()
            }
            
            // Check boundaries
            if (earthY > height + 100f || earthX < -100f || earthX > width + 100f) {
                resetEarth()
            }
        }
        
        // Update asteroid physics
        asteroidX += asteroidVelocityX
        
        // Bounce asteroid off walls
        if (asteroidX < asteroidRadius || asteroidX > width - asteroidRadius) {
            asteroidVelocityX *= -1
        }
    }
    
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!isEarthLaunched && isGameActive) {
                    // Check if touch is near Earth
                    val touchX = event.x
                    val touchY = event.y
                    val distance = sqrt((touchX - earthX).pow(2) + (touchY - earthY).pow(2))
                    
                    if (distance <= earthRadius + 50f) {
                        isPulling = true
                        pullStartX = touchX
                        pullStartY = touchY
                        currentPullX = touchX
                        currentPullY = touchY
                        return true
                    }
                }
            }
            
            MotionEvent.ACTION_MOVE -> {
                if (isPulling && !isEarthLaunched) {
                    currentPullX = event.x
                    currentPullY = event.y
                    
                    // Limit pull distance
                    val deltaX = currentPullX - earthX
                    val deltaY = currentPullY - earthY
                    val distance = sqrt(deltaX * deltaX + deltaY * deltaY)
                    
                    if (distance > maxPullDistance) {
                        val scale = maxPullDistance / distance
                        currentPullX = earthX + deltaX * scale
                        currentPullY = earthY + deltaY * scale
                    }
                    
                    return true
                }
            }
            
            MotionEvent.ACTION_UP -> {
                if (isPulling && !isEarthLaunched) {
                    // Calculate launch velocity based on pull
                    val deltaX = earthX - currentPullX
                    val deltaY = earthY - currentPullY
                    val distance = sqrt(deltaX * deltaX + deltaY * deltaY)
                    
                    if (distance > 20f) { // Minimum pull distance
                        // Launch Earth
                        earthVelocityX = deltaX * forceMultiplier
                        earthVelocityY = deltaY * forceMultiplier
                        isEarthLaunched = true
                        gameListener?.onShotFired()
                    }
                    
                    isPulling = false
                    return true
                }
            }
        }
        
        return super.onTouchEvent(event)
    }
} 