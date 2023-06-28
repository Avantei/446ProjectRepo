package com.example.mealplanner.ui.eventDetailActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.example.mealplanner.databinding.ActivityEventBinding

class EventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventBinding
    private lateinit var viewModel: EventDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, EventDetailViewModel.Factory)[EventDetailViewModel::class.java]

        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup adaptor for tabs
        val pagerAdapter = EventPagerAdapter(this, this)
        val viewPager: ViewPager2 = binding.viewPager
        val tabs: TabLayout = binding.tabs
        viewPager.adapter = pagerAdapter
        pagerAdapter.attachMediator(tabs, viewPager)
    }
}