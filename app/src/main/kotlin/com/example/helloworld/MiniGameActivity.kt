package com.example.helloworld

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import kotlin.math.*
import kotlin.random.Random

class MiniGameActivity : BaseActivity() {
    
    private lateinit var backButton: MaterialButton
    private lateinit var resetButton: MaterialButton
    private lateinit var scoreTextView: TextView
    private lateinit var gameView: GameView
    
    private var score = 0
    private var shots = 0
    private var hits = 0
    
    interface GameListener {
        fun onAsteroidHit()
        fun onShotFired()
    }
    
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_mini_game
    }
    
    override fun initializeActivity() {
        initializeViews()
        setupClickListeners()
        updateScore()
    }
    
    private fun initializeViews() {
        backButton = findViewById(R.id.backButton)
        resetButton = findViewById(R.id.resetButton)
        scoreTextView = findViewById(R.id.scoreTextView)
        gameView = findViewById(R.id.gameView)
        
        gameView.setOnGameListener(object : GameListener {
            override fun onAsteroidHit() {
                hits++
                score += 100
                updateScore()
                
                if (hits >= 4) {
                    Toast.makeText(this@MiniGameActivity, "Asteroid Destroyed! 🎉", Toast.LENGTH_LONG).show()
                    gameView.resetAsteroid()
                    hits = 0
                }
            }
            
            override fun onShotFired() {
                shots++
                updateScore()
            }
        })
    }
    
    private fun setupClickListeners() {
        backButton.setOnClickListener {
            finish()
        }
        
        resetButton.setOnClickListener {
            resetGame()
        }
    }
    
    private fun updateScore() {
        val accuracy = if (shots > 0) (hits * 100 / shots) else 0
        scoreTextView.text = "Score: $score | Shots: $shots | Hits: $hits | Accuracy: ${accuracy}%"
    }
    
    private fun resetGame() {
        score = 0
        shots = 0
        hits = 0
        gameView.resetGame()
        updateScore()
        Toast.makeText(this, "Game Reset!", Toast.LENGTH_SHORT).show()
    }
} 