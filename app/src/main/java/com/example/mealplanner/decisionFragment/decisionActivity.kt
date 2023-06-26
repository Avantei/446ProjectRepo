package com.example.mealplanner.decisionFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mealplanner.databinding.ActivityDecisionBinding
import com.example.mealplanner.R

// chatGPT was used for parts of this file

class decisionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDecisionBinding
    lateinit var restaurantName: String
    var restaurantImageResourceId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDecisionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the passed parameters
        restaurantName = intent.getStringExtra("restaurantName") ?: "placeholder"
        restaurantImageResourceId = intent.getIntExtra("restaurantImage", R.drawable.default_restaurant_image)

        // Update the views with the passed data
        binding.textRestaurant.text = restaurantName
        binding.imageRestaurant.setImageResource(restaurantImageResourceId)
    }
}