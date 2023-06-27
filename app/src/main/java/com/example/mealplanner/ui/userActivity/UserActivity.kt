package com.example.mealplanner.ui.userActivity

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.mealplanner.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Create ViewModel
        viewModel = ViewModelProvider(this, UserViewModel.Factory)[UserViewModel::class.java]
        // Inflate
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up and connect pager with tabs
        val pagerAdapter = UserPagerAdapter(this, this)
        val viewPager: ViewPager2 = binding.viewPager
        val tabs: TabLayout = binding.tabs
        viewPager.adapter = pagerAdapter
        pagerAdapter.attachMediator(tabs, viewPager)

        // Set up title
        viewModel.username.observe(this) {
            title = it
        }
    }
}