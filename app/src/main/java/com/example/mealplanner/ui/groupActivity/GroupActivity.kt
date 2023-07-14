package com.example.mealplanner.ui.groupActivity

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.mealplanner.R
import com.example.mealplanner.databinding.ActivityGroupBinding
import com.example.mealplanner.ui.userActivity.UserPagerAdapter
import com.google.android.material.tabs.TabLayout

class GroupActivity : AppCompatActivity() {

    // private lateinit var viewModel: GroupViewModel
    private lateinit var binding: ActivityGroupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create ViewModel
        // viewModel = ViewModelProvider(this, UserViewModel.Factory)[UserViewModel::class.java]

        // Inflate
        binding = ActivityGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up and connect pager with tabs
        val pagerAdapter = GroupPagerAdapter(this, this)
        val viewPager: ViewPager2 = binding.viewPager
        val tabs: TabLayout = binding.tabs
        viewPager.adapter = pagerAdapter
        pagerAdapter.attachMediator(tabs, viewPager)
    }
}