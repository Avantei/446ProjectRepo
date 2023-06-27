package com.example.mealplanner.ui.userActivity

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
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
        // Setup adaptor for tabs
        val userSectionsPagerAdapter = UserSectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = userSectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
        // Set up title
        viewModel.username.observe(this) {
            title = it
        }
    }
}