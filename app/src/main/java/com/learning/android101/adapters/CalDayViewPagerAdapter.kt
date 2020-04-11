package com.learning.android101.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.learning.android101.Constants
import com.learning.android101.fragments.CalDayFragment
import com.learning.android101.interfaces.CalNavigationListener

class CalDayViewPagerAdapter(fm: FragmentManager, daysArray: List<String>, listener: CalNavigationListener) : FragmentStatePagerAdapter(fm) {
    private var daysArray = daysArray                               // days to display
    private var listener = listener

    override fun getItem(position: Int): Fragment {
        val fragment = CalDayFragment()
        val bundle = Bundle()
        bundle.putString(Constants.ARG_DATE, "${daysArray[position]}")
        fragment.arguments = bundle
        fragment.navListener = listener
        return fragment
    }

    override fun getCount(): Int {
        return daysArray.size
    }

    fun updateDaysArray(updatedMonthsArray: List<String>) {
        daysArray = updatedMonthsArray
    }
}