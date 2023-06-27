package com.example.mealplanner.ui.eventDetailActivity


import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.mealplanner.R
import com.example.mealplanner.ui.locationFragment.LocationFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

// Tabs and their name
private val TAB_TITLES = arrayOf(
    R.string.time_tab_name,
    R.string.location_tab_name,
    R.string.transport_tab_name,
    R.string.bill_tab_name,
    R.string.decision_tab_name
)

class EventPagerAdapter(private val context: Context, fa: FragmentActivity) :
    FragmentStateAdapter(fa) {
    override fun getItemCount() = TAB_TITLES.size

    override fun createFragment(position: Int): Fragment {
        // What fragment to return for each index in the TAB_TITLES
        return when (position) {
            0 -> TimePickerFragment()
            1 -> LocationFragment()
            2 -> DecisionFragment() // TODO: replace with RSVP fragment
            3 -> DecisionFragment() // TODO: replace with Location fragment
            4 -> DecisionFragment()
            else -> DecisionFragment()
        }
    }

    fun attachMediator(tabs: TabLayout, viewPager: ViewPager2) {
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = context.resources.getString(TAB_TITLES[position])
        }.attach()
    }

}