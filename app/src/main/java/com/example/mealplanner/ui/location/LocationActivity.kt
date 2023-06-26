package com.example.mealplanner.ui.location

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealplanner.R
import com.example.mealplanner.databinding.ActivityLocationBinding
import com.example.mealplanner.ui.login.LoginViewModel
import com.example.mealplanner.ui.login.LoginViewModelFactory

class LocationActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LocationViewModel
    private lateinit var binding: ActivityLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        val viewModel =
            ViewModelProvider(this, LocationViewModel.Factory)[LocationViewModel::class.java]

        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val text = binding.editTextText
        val button = binding.button

        val location = binding.rvLocation
        val adapter = viewModel.locationlist.value?.let { LocationsAdapter(it) }
        button.setOnClickListener {
            viewModel.locationlist.value?.add(0, LocationSuggestion(text.text.toString(), 0))
            adapter?.notifyItemInserted(0)
        }
        location.adapter = adapter
        // Set layout manager to position the items
        location.layoutManager = LinearLayoutManager(this)

    }
}