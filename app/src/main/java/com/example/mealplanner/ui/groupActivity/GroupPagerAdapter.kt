package com.example.mealplanner.ui.groupActivity

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.mealplanner.R
import com.example.mealplanner.ui.groupActivity.bills.BillsFragment
import com.example.mealplanner.ui.groupActivity.events.EventsFragment
import com.example.mealplanner.ui.groupActivity.members.MembersFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

private val TAB_TITLES = listOf(
    R.string.title_events,
    R.string.title_members,
    R.string.title_bills
)

class GroupPagerAdapter (private val context: Context, ga: GroupActivity) :
    FragmentStateAdapter(ga) {
        override fun getItemCount() = TAB_TITLES.size
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> EventsFragment()
                1 -> MembersFragment()
                2 -> BillsFragment()
                else -> EventsFragment()
            }
        }
        fun attachMediator(tabs: TabLayout, viewPager: ViewPager2) {
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = context.resources.getString(TAB_TITLES[position])
            }.attach()
        }
}