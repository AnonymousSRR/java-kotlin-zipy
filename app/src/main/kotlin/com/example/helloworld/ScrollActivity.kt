package com.example.helloworld

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView as AndroidTextView

class ScrollActivity : BaseActivity() {
    
    private lateinit var backButton: MaterialButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var scrollAdapter: ScrollAdapter
    
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_scroll
    }
    
    override fun initializeActivity() {
        initializeViews()
        setupRecyclerView()
        setupClickListeners()
    }
    
    private fun initializeViews() {
        backButton = findViewById(R.id.backButton)
        recyclerView = findViewById(R.id.recyclerView)
    }
    
    private fun setupRecyclerView() {
        // Create rows of cards for horizontal scrolling
        val rowData = generateRowData(100) // 100 rows for infinite vertical scrolling
        
        scrollAdapter = ScrollAdapter(rowData)
        
        // Use LinearLayoutManager for vertical scrolling of rows
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = scrollAdapter
        
        // Enable smooth scrolling
        recyclerView.setHasFixedSize(false)
    }
    
    private fun setupClickListeners() {
        backButton.setOnClickListener {
            finish()
        }
    }
    
    private fun generateRowData(rowCount: Int): List<RowData> {
        val rows = mutableListOf<RowData>()
        
        for (rowIndex in 0 until rowCount) {
            val cardsInRow = generateCardData(50) // 50 cards per row for horizontal scrolling
            val rowData = RowData(rowIndex, cardsInRow)
            rows.add(rowData)
        }
        
        return rows
    }
    
    private fun generateCardData(count: Int): List<CardData> {
        val cards = mutableListOf<CardData>()
        val titles = listOf(
            "Feature Card", "Info Card", "Action Card", "Status Card", "Data Card",
            "Settings Card", "Profile Card", "Notification Card", "Message Card", "Alert Card",
            "Success Card", "Warning Card", "Error Card", "Loading Card", "Empty Card",
            "Custom Card", "Dynamic Card", "Interactive Card", "Animated Card", "Responsive Card"
        )
        
        val descriptions = listOf(
            "This is a sample card with some content",
            "Another card with different information",
            "Card showing various data points",
            "Interactive card with user actions",
            "Card displaying system status",
            "Information card with details",
            "Action card for user interactions",
            "Status card showing current state",
            "Data card with statistics",
            "Settings card for configuration"
        )
        
        val colors = listOf(
            android.graphics.Color.parseColor("#FF5722"), // Deep Orange
            android.graphics.Color.parseColor("#2196F3"), // Blue
            android.graphics.Color.parseColor("#4CAF50"), // Green
            android.graphics.Color.parseColor("#FF9800"), // Orange
            android.graphics.Color.parseColor("#9C27B0"), // Purple
            android.graphics.Color.parseColor("#607D8B"), // Blue Grey
            android.graphics.Color.parseColor("#E91E63"), // Pink
            android.graphics.Color.parseColor("#795548"), // Brown
            android.graphics.Color.parseColor("#3F51B5"), // Indigo
            android.graphics.Color.parseColor("#009688")  // Teal
        )
        
        for (i in 0 until count) {
            val cardData = CardData(
                id = i,
                title = titles[i % titles.size],
                description = descriptions[i % descriptions.size],
                color = colors[i % colors.size],
                number = i + 1
            )
            cards.add(cardData)
        }
        
        return cards
    }
}

// Data class for row information
data class RowData(
    val rowIndex: Int,
    val cards: List<CardData>
)

// Data class for card information
data class CardData(
    val id: Int,
    val title: String,
    val description: String,
    val color: Int,
    val number: Int
)

// Main RecyclerView Adapter for Rows
class ScrollAdapter(private val rowDataList: List<RowData>) : 
    RecyclerView.Adapter<ScrollAdapter.RowViewHolder>() {
    
    class RowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val horizontalRecyclerView: RecyclerView = itemView.findViewById(R.id.horizontalRecyclerView)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_scroll_row, parent, false)
        return RowViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: RowViewHolder, position: Int) {
        val rowData = rowDataList[position]
        
        // Set up horizontal RecyclerView for this row
        val horizontalAdapter = HorizontalCardAdapter(rowData.cards)
        val horizontalLayoutManager = LinearLayoutManager(
            holder.itemView.context, 
            LinearLayoutManager.HORIZONTAL, 
            false
        )
        
        holder.horizontalRecyclerView.layoutManager = horizontalLayoutManager
        holder.horizontalRecyclerView.adapter = horizontalAdapter
        holder.horizontalRecyclerView.setHasFixedSize(false)
    }
    
    override fun getItemCount(): Int = rowDataList.size
}

// Horizontal RecyclerView Adapter for Cards
class HorizontalCardAdapter(private val cardDataList: List<CardData>) : 
    RecyclerView.Adapter<HorizontalCardAdapter.CardViewHolder>() {
    
    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: com.google.android.material.card.MaterialCardView = itemView.findViewById(R.id.cardView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val numberTextView: TextView = itemView.findViewById(R.id.numberTextView)
        val colorView: View = itemView.findViewById(R.id.colorView)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_scroll_card, parent, false)
        return CardViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val cardData = cardDataList[position]
        
        holder.titleTextView.text = cardData.title
        holder.descriptionTextView.text = cardData.description
        holder.numberTextView.text = "#${cardData.number}"
        holder.colorView.setBackgroundColor(cardData.color)
        
        // Add some random height variation for visual interest
        // val randomHeight = Random.nextInt(200, 300) // Random is removed, so this line is commented out
        // holder.cardView.layoutParams.height = randomHeight
        
        // Add click listener for card interaction
        holder.cardView.setOnClickListener {
            // You can add card click functionality here
            // For now, just show a toast or perform some action
            Toast.makeText(holder.itemView.context, "Card clicked: ${cardData.title}", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun getItemCount(): Int = cardDataList.size
} 