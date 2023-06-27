package com.example.mealplanner.ui.userActivity

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.mealplanner.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserPagerAdapter(private val context: Context, fa: FragmentActivity) :
    FragmentStateAdapter(fa) {
    private val _tabs = listOf(
        R.string.groups_tab_name,
        R.string.profile_tab_name
    )

    override fun getItemCount() = _tabs.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> GroupsFragment()
            1 -> ProfileFragment()
            else -> ProfileFragment()
        }
    }

    fun attachMediator(tabs: TabLayout, viewPager: ViewPager2) {
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = context.resources.getString(_tabs[position])
        }.attach()
    }
}