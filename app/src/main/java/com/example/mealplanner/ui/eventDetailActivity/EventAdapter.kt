package com.example.mealplanner.ui.eventDetailActivity

import com.example.mealplanner.ui.user.GroupsFragment
import com.example.mealplanner.ui.user.ProfileFragment

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.mealplanner.R

// Tabs and their name
private val TAB_TITLES = arrayOf(
    R.string.time_tab_name,
    R.string.location_tab_name,
    R.string.transport_tab_name,
    R.string.bill_tab_name,
    R.string.decision_tab_name
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class EventAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // What fragment to return for each index in the TAB_TITLES
        return when (position) {
            0 -> DecisionFragment()
            1 -> TimePickerFragment()
            else -> DecisionFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }
}