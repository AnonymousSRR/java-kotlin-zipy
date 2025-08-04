package com.example.helloworld

import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.button.MaterialButton
import android.graphics.Color
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup as RecyclerViewViewGroup

class DragDropActivity : BaseActivity() {
    
    private lateinit var backButton: MaterialButton
    private lateinit var sourceContainer: LinearLayout
    private lateinit var targetContainer: LinearLayout
    private lateinit var statusTextView: TextView
    private lateinit var scoreTextView: TextView
    private lateinit var resetButton: MaterialButton
    
    private var score = 0
    private var totalItems = 0
    private var droppedItems = 0
    
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_drag_drop
    }
    
    override fun initializeActivity() {
        initializeViews()
        setupDragAndDrop()
        setupClickListeners()
        createDraggableItems()
    }
    
    private fun initializeViews() {
        backButton = findViewById(R.id.backButton)
        sourceContainer = findViewById(R.id.sourceContainer)
        targetContainer = findViewById(R.id.targetContainer)
        statusTextView = findViewById(R.id.statusTextView)
        scoreTextView = findViewById(R.id.scoreTextView)
        resetButton = findViewById(R.id.resetButton)
    }
    
    private fun setupClickListeners() {
        backButton.setOnClickListener {
            finish()
        }
        
        resetButton.setOnClickListener {
            resetGame()
        }
    }
    
    private fun setupDragAndDrop() {
        // Set up drag listeners for target container
        targetContainer.setOnDragListener { view, event ->
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    view.alpha = 0.7f
                    true
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    view.alpha = 0.9f
                    view.background = getDrawable(R.drawable.target_container_active)
                    true
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                    view.alpha = 0.7f
                    view.background = getDrawable(R.drawable.target_container_normal)
                    true
                }
                DragEvent.ACTION_DROP -> {
                    view.alpha = 1.0f
                    view.background = getDrawable(R.drawable.target_container_normal)
                    
                    val draggedView = event.localState as View
                    val draggedData = draggedView.tag as? DraggableItem
                    
                    if (draggedData != null) {
                        handleDrop(draggedView, draggedData)
                    }
                    true
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    view.alpha = 1.0f
                    view.background = getDrawable(R.drawable.target_container_normal)
                    true
                }
                else -> false
            }
        }
    }
    
    private fun createDraggableItems() {
        val items = listOf(
            DraggableItem("🚀", "Rocket", "Fast and powerful", Color.parseColor("#FF5722")),
            DraggableItem("⭐", "Star", "Bright and shining", Color.parseColor("#FFC107")),
            DraggableItem("💎", "Diamond", "Precious and rare", Color.parseColor("#2196F3")),
            DraggableItem("🌙", "Moon", "Mysterious and calm", Color.parseColor("#9C27B0")),
            DraggableItem("🔥", "Fire", "Hot and energetic", Color.parseColor("#F44336")),
            DraggableItem("🌊", "Wave", "Flowing and dynamic", Color.parseColor("#00BCD4")),
            DraggableItem("🌳", "Tree", "Natural and strong", Color.parseColor("#4CAF50")),
            DraggableItem("⚡", "Lightning", "Quick and powerful", Color.parseColor("#FF9800"))
        )
        
        totalItems = items.size
        
        items.forEach { item ->
            val draggableCard = createDraggableCard(item)
            sourceContainer.addView(draggableCard)
        }
        
        updateStatus("Drag items to the target area!")
    }
    
    private fun createDraggableCard(item: DraggableItem): CardView {
        val cardView = CardView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(8, 8, 8, 8)
            }
            radius = 12f
            elevation = 4f
            tag = item
        }
        
        val contentLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 16, 16, 16)
            background = getDrawable(R.drawable.draggable_item_background)
        }
        
        val iconTextView = TextView(this).apply {
            text = item.icon
            textSize = 32f
            gravity = android.view.Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        
        val titleTextView = TextView(this).apply {
            text = item.title
            textSize = 16f
            setTextColor(Color.BLACK)
            gravity = android.view.Gravity.CENTER
            setTypeface(null, android.graphics.Typeface.BOLD)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 8
            }
        }
        
        val descriptionTextView = TextView(this).apply {
            text = item.description
            textSize = 12f
            setTextColor(Color.GRAY)
            gravity = android.view.Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 4
            }
        }
        
        contentLayout.addView(iconTextView)
        contentLayout.addView(titleTextView)
        contentLayout.addView(descriptionTextView)
        cardView.addView(contentLayout)
        
        // Set up drag listener
        cardView.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val shadowBuilder = View.DragShadowBuilder(view)
                    view.startDragAndDrop(null, shadowBuilder, view, 0)
                    view.alpha = 0.5f
                    true
                }
                MotionEvent.ACTION_UP -> {
                    view.alpha = 1.0f
                    true
                }
                else -> false
            }
        }
        
        return cardView
    }
    
    private fun handleDrop(draggedView: View, item: DraggableItem) {
        // Remove from source container
        val parent = draggedView.parent as ViewGroup
        parent.removeView(draggedView)
        
        // Create dropped item view
        val droppedView = createDroppedItemView(item)
        targetContainer.addView(droppedView)
        
        // Update score and status
        droppedItems++
        score += 10
        updateScore()
        
        // Play success animation
        val animation = AnimationUtils.loadAnimation(this, R.anim.drop_success)
        droppedView.startAnimation(animation)
        
        updateStatus("${item.title} dropped successfully! +10 points")
        
        // Check if game is complete
        if (droppedItems >= totalItems) {
            showGameComplete()
        }
    }
    
    private fun createDroppedItemView(item: DraggableItem): CardView {
        val cardView = CardView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(8, 8, 8, 8)
            }
            radius = 12f
            elevation = 8f
        }
        
        val contentLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(16, 16, 16, 16)
            background = getDrawable(R.drawable.dropped_item_background)
            gravity = android.view.Gravity.CENTER_VERTICAL
        }
        
        val iconTextView = TextView(this).apply {
            text = item.icon
            textSize = 24f
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        
        val textLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                marginStart = 16
            }
        }
        
        val titleTextView = TextView(this).apply {
            text = item.title
            textSize = 16f
            setTextColor(Color.WHITE)
            setTypeface(null, android.graphics.Typeface.BOLD)
        }
        
        val descriptionTextView = TextView(this).apply {
            text = item.description
            textSize = 12f
            setTextColor(Color.LTGRAY)
        }
        
        textLayout.addView(titleTextView)
        textLayout.addView(descriptionTextView)
        
        contentLayout.addView(iconTextView)
        contentLayout.addView(textLayout)
        cardView.addView(contentLayout)
        
        return cardView
    }
    
    private fun updateStatus(message: String) {
        statusTextView.text = message
    }
    
    private fun updateScore() {
        scoreTextView.text = "Score: $score"
    }
    
    private fun resetGame() {
        // Clear target container
        targetContainer.removeAllViews()
        
        // Reset score and counters
        score = 0
        droppedItems = 0
        updateScore()
        
        // Recreate source items
        sourceContainer.removeAllViews()
        createDraggableItems()
        
        updateStatus("Game reset! Drag items to the target area!")
        Toast.makeText(this, "Game reset!", Toast.LENGTH_SHORT).show()
    }
    
    private fun showGameComplete() {
        updateStatus("🎉 All items dropped! Final score: $score")
        Toast.makeText(this, "Congratulations! You completed the drag and drop challenge!", Toast.LENGTH_LONG).show()
    }
}

// Data class for draggable items
data class DraggableItem(
    val icon: String,
    val title: String,
    val description: String,
    val color: Int
) 