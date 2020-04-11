package com.learning.android101.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.learning.android101.Constants
import com.learning.android101.fragments.CalYearFragment

class CalYearViewPagerAdapter(fm: FragmentManager, yearsArray: List<Int>) : FragmentStatePagerAdapter(fm) {

    private var yearsArray = yearsArray                         // years to display

    override fun getItem(position: Int): Fragment {
        val fragment = CalYearFragment()
        val bundle = Bundle()
        bundle.putInt(Constants.ARG_YEAR, yearsArray[position])
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int {
        return yearsArray.size
    }

    fun updateYearsArray(updatedYearsArray: List<Int>) {
        yearsArray = updatedYearsArray
    }

}